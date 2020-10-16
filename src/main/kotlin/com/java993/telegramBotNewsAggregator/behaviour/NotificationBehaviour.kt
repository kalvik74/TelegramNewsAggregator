package com.java993.telegramBotNewsAggregator.behaviour

import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationBehaviour : Behaviour {

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    override fun parse(update: Update?) {
        when {
            update?.message?.text?.startsWith("/start") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id.toString(),
                                text = "news notification enabled"

                        )
                )
            }
            update?.message?.text?.startsWith("/stop") == true -> {
                val message = update.extractMessage()!!
                telegramSender.executeMethod(
                        SendMessageRequest(
                                chatId = message.chat.id.toString(),
                                text = "news notification disabled"

                        )
                )
            }
        }
    }
}