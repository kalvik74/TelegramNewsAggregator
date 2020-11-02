package com.java993.telegramBotNewsAggregator

import com.java993.telegramBotNewsAggregator.behaviour.model.RedditSetting
import com.java993.telegramBotNewsAggregator.behaviour.model.RssSetting
import com.java993.telegramBotNewsAggregator.behaviour.model.Setting
import com.java993.telegramBotNewsAggregator.behaviour.model.User
import org.artfable.telegram.api.Chat
import org.artfable.telegram.api.ChatType
import org.artfable.telegram.api.Message
import org.artfable.telegram.api.Update

fun createModelUser(id: Long = 1, chatId: Long = 1, name: String = ""): User {
    return User(
            id = id,
            chatId = chatId,
            name = name,
            settings = Setting(
                    enableNotification = true,
                    rss = RssSetting(url = listOf(), searchFilters = listOf()),
                    reddit = RedditSetting(communities = listOf(), searchFilters = listOf())
            )
    )
}


fun createFrom(id: Long = 1, userName: String = "", firstName: String): org.artfable.telegram.api.User? = org.artfable.telegram.api.User(
        id = id,
        username = userName,
        firstName = firstName
)

fun createUpdate(
        id: Long = 1, message: Message): Update = Update(
        updateId = id,
        message = message
)

fun createMessage(messageId: Long = 1, text: String = "", chatId: Long = 1, from: org.artfable.telegram.api.User? = null): Message = Message(
        messageId = messageId,
        date = 1,
        text = text,
        chat = Chat(
                id = chatId,
                type = ChatType.CHANNEL
        ),
        from = from
)
