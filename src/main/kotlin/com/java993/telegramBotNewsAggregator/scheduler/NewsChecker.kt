package com.java993.telegramBotNewsAggregator.scheduler

import com.java993.telegramBotNewsAggregator.scheduler.searcher.Searcher
import com.java993.telegramBotNewsAggregator.service.UserService
import org.artfable.telegram.api.ParseMode
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NewsChecker {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var searchers: List<Searcher>

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var telegramSender: TelegramSender;

    @Scheduled(initialDelayString = "\${news.checker.job.initialDelay}", fixedDelayString = "\${news.checker.job.fixedDelay}")
    fun sendNews() {
        userService.getAllUsersWithEnabledNotification().forEach { user ->
            searchers.map { searcher ->
                searcher.getNews(userId = user.id)
            }.flatten().let { news ->
                logger.info("${news.count()} news found for user ${user.name} (${user.id})")
                if (news.isNotEmpty()) {
                    telegramSender.executeMethod(
                            SendMessageRequest(
                                    chatId = user.chatId.toString(),
                                    text = news.map { "`" + it.text + "`" }.reduce { acc, s -> acc + s + "\n" },
                                    parseMode = ParseMode.MARKDOWN_V2
                            )
                    )
                }
            }
        }

    }
}