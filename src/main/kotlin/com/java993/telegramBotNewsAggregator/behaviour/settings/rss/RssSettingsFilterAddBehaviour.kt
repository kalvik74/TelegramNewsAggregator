package com.java993.telegramBotNewsAggregator.behaviour.settings.rss

import com.java993.telegramBotNewsAggregator.service.UserService
import com.java993.telegramBotNewsAggregator.utils.UrlUtils.UrlUtils.isValidURL
import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.Update
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RssSettingsFilterAddBehaviour : Behaviour {

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Autowired
    private lateinit var userService: UserService;

    private val state = mutableSetOf<Long>()

    override fun parse(update: Update?) {
        val message = update?.extractMessage()!!
        when {
            message.text?.startsWith("/new_rss_filter") == true -> {
                message.from?.let {
                    state.add(it.id)
                    telegramSender.executeMethod(
                            SendMessageRequest(
                                    chatId = message.chat.id.toString(),
                                    text = "please enter searching filter (example: java)"
                            )
                    )
                }
            }

            //delete remove state if user choose different command
            message.text?.startsWith("/")!! && state.contains(message.from?.id) -> {
                state.remove(message.from?.id);
            }

            state.contains(message.from?.id) -> {
                message.text?.let { filter ->
                    message.from?.let { from ->
                        state.remove(from.id)
                        val user = userService.updateUserInfoAndGet(userId = from.id, userName = from.username.toString(), chatId = message.chat.id)
                        val userCopy = user.copy(
                                settings = user.settings.copy(
                                        rss = user.settings.rss.copy(
                                                searchFilters = user.settings.rss.searchFilters + filter
                                        )
                                )
                        )
                        userService.save(userCopy)

                        telegramSender.executeMethod(
                                SendMessageRequest(
                                        chatId = message.chat.id.toString(),
                                        text = "Searching filter $filter added successfully"
                                )
                        )
                    }

                }
            }

        }
    }

}