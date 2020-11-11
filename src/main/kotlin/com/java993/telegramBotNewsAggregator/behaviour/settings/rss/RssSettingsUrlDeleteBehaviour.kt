package com.java993.telegramBotNewsAggregator.behaviour.settings.rss

import com.java993.telegramBotNewsAggregator.service.UserService
import com.java993.telegramBotNewsAggregator.utils.UrlUtils
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.keyboard.InlineKeyboard
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RssSettingsUrlDeleteBehaviour : Behaviour {


    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;

    @Autowired
    private lateinit var rssSettingsUrlDeleteButtonBehaviour: RssSettingsUrlDeleteButtonBehaviour;

    override fun parse(update: Update?) {
        val message = update?.extractMessage()!!
        when {
            message.text?.startsWith("/del_rss_url") == true -> {
                message.from?.let {
                    val user = userService.updateUserInfoAndGet(
                            userId = it.id,
                            userName = it.username.toString(),
                            chatId = message.chat.id
                    )
                    telegramSender.executeMethod(
                            SendMessageRequest(
                                    chatId = message.chat.id.toString(),
                                    text = "*Choose Url for deletion*:  \n \n",
                                    replyMarkup = InlineKeyboard(
                                            user.settings.rss.url.sorted().mapIndexed { index, url ->
                                                rssSettingsUrlDeleteButtonBehaviour.createBtn(url, index.toString())
                                            }.chunked(1).map { it.toTypedArray() }.toTypedArray()
                                    ),

                                            parseMode = ParseMode.MARKDOWN_V2
                                    )
                            )
                }
            }
        }
    }
}