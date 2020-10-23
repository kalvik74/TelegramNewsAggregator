package com.java993.telegramBotNewsAggregator.behaviour

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
                                    text = "your settings: \nID: ${user.id} \nNAME: ${user.name} \nCHATID: ${user.chatId}" +
                                            "\nNOTIFICATION: ${user.settings.enableNotification}" +
                                            "\nRSS: \n urls: ${user.settings.rss.url.map { "\n -$it" }} \n filters: ${user.settings.rss.searchFilters.map { "\n -$it" }}" +
                                            "\nREDDIT: \n communities: ${user.settings.reddit.communities.map { "\n -$it" }} \n" +
                                            " filters: ${user.settings.reddit.searchFilters.map { "\n -$it" }}"
                            )
                    )
                }
            }
        }
    }

}