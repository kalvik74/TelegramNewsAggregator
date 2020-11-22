package com.java993.telegramBotNewsAggregator.dao

import com.java993.telegramBotNewsAggregator.dao.entity.settings.RedditSettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.settings.RssSettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.settings.SettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.UserEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest @Autowired constructor(
        val userRepository: UserRepository) {

    @Test
    fun `When findById then return UserEntity`() {
        val user = UserEntity(
                id = 6,
                name = "John",
                chatId = 6,
                settings = SettingEntity(
                        enableNotification = true,
                        rss = RssSettingEntity(
                                listOf(
                                        "http://test1.ru",
                                        "http://test2.ru"
                                ),
                                listOf(
                                        "search1",
                                        "search2"
                                )
                        ),
                        reddit = RedditSettingEntity(
                                listOf(
                                        "java",
                                        "kotlin"
                                ),
                                listOf(
                                        "heap",
                                        "jvm"
                                )
                        )
                )

        );
        userRepository.save(user);

        val found = userRepository.findById(user.id)

        assertThat(found.get()).isEqualTo(user)
    }
}