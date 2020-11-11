package com.java993.telegramBotNewsAggregator.behaviour.settings.rss

import com.java993.telegramBotNewsAggregator.service.UserService
import org.artfable.telegram.api.*
import org.artfable.telegram.api.keyboard.InlineKeyboardBtn
import org.artfable.telegram.api.request.DeleteMessageRequest
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class RssSettingsUrlDeleteButtonBehaviour : AbstractCallbackBehaviour("deleteRssUrl") {


    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;

    override fun parse(value: String?, callbackQuery: CallbackQuery?) {
        callbackQuery?.from?.id?.let {
            userService.getUserById(it).get()
        }?.let { user ->
            value?.let {
                val newUrlList = user.settings.rss.url.sorted().toMutableList()
                newUrlList.removeAt(value.toInt());
                userService.save(
                        user.copy(
                                settings = user.settings.copy(
                                        rss = user.settings.rss.copy(
                                                url = newUrlList
                                        )
                                )
                        )
                )
                telegramSender.executeMethod(
                        DeleteMessageRequest(
                                chatId = callbackQuery?.message?.chat?.id!!,
                                messageId = callbackQuery.message?.messageId!!

                        )
                )
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = callbackQuery.message?.chat?.id!!.toString(),
                                text = "url \"${user.settings.rss.url[value.toInt()]}\" was deleted"

                        )
                )
            }
        }
    }

}