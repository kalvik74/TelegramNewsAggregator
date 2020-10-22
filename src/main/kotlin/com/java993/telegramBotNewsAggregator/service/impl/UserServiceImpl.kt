package com.java993.telegramBotNewsAggregator.service.impl

import com.java993.telegramBotNewsAggregator.behaviour.model.RedditSetting
import com.java993.telegramBotNewsAggregator.behaviour.model.RssSetting
import com.java993.telegramBotNewsAggregator.behaviour.model.Setting
import com.java993.telegramBotNewsAggregator.behaviour.model.User
import com.java993.telegramBotNewsAggregator.dao.UserRepository
import com.java993.telegramBotNewsAggregator.dao.entity.RedditSettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.RssSettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.SettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.UserEntity
import com.java993.telegramBotNewsAggregator.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository;

    override fun getUserById(userId: Long): Optional<User> = userRepository.findById(userId).map { User(it) };

     override fun updateUserInfoAndGet(userId: Long, userName: String, chatId: Long): User {
        var user = getUserById(userId)
        if (user.isEmpty) {
            val userEntity = UserEntity(
                    id = userId,
                    name = userName,
                    chatId = chatId,
                    settings = SettingEntity(
                            enableNotification = false,
                            rss = RssSettingEntity(
                                    url = ArrayList(),
                                    searchFilters = ArrayList()
                            ),
                            reddit = RedditSettingEntity(
                                    communities = ArrayList(),
                                    searchFilter = ArrayList()
                            )
                    )
            )
            userRepository.save(userEntity)
            return User(userEntity)
        } else if (user.get().chatId != chatId) {
            user = Optional.of(user.get().copy(chatId = chatId));
            userRepository.save(UserEntity(user.get()));
        }
        return user.get();
    }

    fun User(entity: UserEntity): User = User(
            id = entity.id,
            chatId = entity.chatId,
            name = entity.name,
            settings = Setting(
                    enableNotification = entity.settings.enableNotification,
                    rss = RssSetting(
                            url = mutableListOf(*entity.settings.rss.url.toTypedArray()),
                            searchFilters = mutableListOf(*entity.settings.rss.searchFilters.toTypedArray())
                    ),
                    reddit = RedditSetting(
                            communities = mutableListOf(*entity.settings.reddit.communities.toTypedArray()),
                            searchFilters = mutableListOf(*entity.settings.reddit.searchFilter.toTypedArray())
                    )
            )
    )

    fun UserEntity(user: User): UserEntity = UserEntity(
            id = user.id,
            chatId = user.chatId,
            name = user.name,
            settings = SettingEntity(
                    enableNotification = user.settings.enableNotification,
                    rss = RssSettingEntity(
                            url = mutableListOf(*user.settings.rss.url.toTypedArray()),
                            searchFilters = mutableListOf(*user.settings.rss.searchFilters.toTypedArray())
                    ),
                    reddit = RedditSettingEntity(
                            communities = mutableListOf(*user.settings.reddit.communities.toTypedArray()),
                            searchFilter = mutableListOf(*user.settings.reddit.searchFilters.toTypedArray())
                    )
            )
    )


    override fun getAllUsers(): List<User> = userRepository.findAll().map { User(it) }

    override fun save(user: User) {
        userRepository.save(UserEntity(user))
    }

    override fun disableNotification(userId: Long) {
        changeNotificationStatus(userId, false)
    }

    override fun enableNotification(userId: Long) {
        changeNotificationStatus(userId, true)
    }

    private fun changeNotificationStatus(userId: Long, status: Boolean) {
        userRepository.findById(userId).map {
            userRepository.save(
                    it.copy(
                            settings = SettingEntity(
                                    rss = it.settings.rss,
                                    reddit = it.settings.reddit,
                                    enableNotification = status
                            )
                    )
            )
        }
    }


}