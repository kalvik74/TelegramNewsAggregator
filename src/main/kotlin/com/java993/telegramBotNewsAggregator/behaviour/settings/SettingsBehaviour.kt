package com.java993.telegramBotNewsAggregator.behaviour.settings

import com.java993.telegramBotNewsAggregator.service.UserService
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SettingsBehaviour : Behaviour {

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;


    override fun parse(update: Update?) {
        when {
            update?.message?.text?.startsWith("/settings") == true -> {
                val message = update?.extractMessage()!!
                message.from?.let {
                    val user = userService.updateUserInfoAndGet(
                            userId = it.id,
                            userName = it.username.toString(),
                            chatId = message.chat.id
                    )
                    telegramSender.executeMethod(
                            SendMessageRequest(
                                    chatId = message.chat.id.toString(),
                                    text = "*Your Settings* \n " +
                                            "Name: `${user.name}`\n " +
                                            "Notification: `${user.settings.enableNotification}` \n \n \n" +
                                            "*Rss Settings* \n " +
                                            "Urls: ${user.settings.rss.url.map { "\n `[$it]`" }} \n \\[*/new\\_rss\\_url*\\] or \\[*/del\\_rss\\_url*\\] \n \n \n" +
                                            "Filters: ${user.settings.rss.searchFilters.map { "\n `[$it]`" }} \n" +
                                            " \\[*/new\\_rss\\_filter*\\] or \\[*/del\\_rss\\_filter*\\] \n" +
                                            " \n" +
                                            " \n",
                                    parseMode = ParseMode.MARKDOWN_V2
                            )
                    )
                }
            }
        }
    }

}