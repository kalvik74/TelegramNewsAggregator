package com.java993.telegramBotNewsAggregator.behaviour

import com.java993.telegramBotNewsAggregator.service.UserService
import com.java993.telegramBotNewsAggregator.utils.UrlUtils.UrlUtils.isValidURL
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RssSettingsUrlAddBehaviour : Behaviour {

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;

    private val state = mutableSetOf<Long>()

    override fun parse(update: Update?) {
        val message = update?.extractMessage()!!
        when {
            message.text?.startsWith("/newrssurl") == true -> {
                message.from?.let {
                    state.add(it.id)
                    telegramSender.executeMethod(
                            SendMessageRequest(
                                    chatId = message.chat.id.toString(),
                                    text = "please enter rss url (example: https://habr.com/ru/rss/)"
                            )
                    )
                }
            }

            //delete remove state if user choose different command
            message.text?.startsWith("/")!! && state.contains(message.from?.id) -> {
                state.remove(message.from?.id);
            }

            state.contains(message.from?.id) -> {
                message.text?.let { url ->
                    message.from?.let { from ->
                        val text = when {
                            isValidURL(url) -> {
                                state.remove(from.id)
                                val user = userService.updateUserInfoAndGet(userId = from.id, userName = from.username.toString(), chatId = message.chat.id)
                                val userCopy = user.copy(
                                        settings = user.settings.copy(
                                                rss = user.settings.rss.copy(
                                                        url = user.settings.rss.url + url
                                                )
                                        )
                                )
                                userService.save(userCopy)
                                "Url $url added successfully"
                            }
                            else -> {
                                "Url $url has mistakes, try again"
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

        }
    }

}