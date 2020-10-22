package com.java993.telegramBotNewsAggregator.behaviour

import com.java993.telegramBotNewsAggregator.service.UserService
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

    @Autowired
    private lateinit var userService: UserService;


    override fun parse(update: Update?) {
        when {
            update?.message?.text?.startsWith("/start") == true -> {
                changeNotificationStatusAndSendMessage(update, true)
            }
            update?.message?.text?.startsWith("/stop") == true -> {
                changeNotificationStatusAndSendMessage(update, false)
            }
        }
    }

    private fun changeNotificationStatusAndSendMessage(update: Update?, status: Boolean) {
        val message = update?.extractMessage()!!
        message.from?.let {
            val user = userService.updateUserInfoAndGet(
                    userId = it.id,
                    userName = it.username.toString(),
                    chatId = message.chat.id
            )

            val text = when(status) {
                true -> {
                    userService.enableNotification(user.id)
                    "news notification enabled"
                }
                false -> {
                    userService.disableNotification(user.id)
                    "news notification disabled"
                }
            }

            telegramSender.executeMethod(
                    SendMessageRequest(
                            chatId = message.chat.id.toString(),
                            text = text

                    )
            )
        }
    }
}