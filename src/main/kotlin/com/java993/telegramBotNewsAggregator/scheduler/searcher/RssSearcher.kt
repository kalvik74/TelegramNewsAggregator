package com.java993.telegramBotNewsAggregator.scheduler.searcher

import com.apptastic.rssreader.Item
import com.apptastic.rssreader.RssReader
import com.java993.telegramBotNewsAggregator.scheduler.searcher.dto.News
import com.java993.telegramBotNewsAggregator.service.NewsService
import com.java993.telegramBotNewsAggregator.service.UserService
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.streams.toList


@Component
class RssSearcher : Searcher {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var newsService: NewsService

    private val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US)

    override fun getNews(userId: Long): List<News> {
        userService.getUserById(userId).get().let { user ->
            return user.settings.rss.url.map { url ->
                logger.info("RSS: begin searching by URL: $url")
                try {
                    RssReader()
                            .read(url)
                            .map { toNews(it) }
                            .filter { news ->
                                !newsService.isNewsAlreadyExist(news.id)
                                        && user.settings.rss.searchFilters.any { filter -> news.text.toLowerCase().contains(filter.toLowerCase()) }
                            }
                            .map { news ->
                                newsService.create(news)
                                news
                            }
                            .toList()
                } catch (e: Exception) {
                    logger.error("error during read rss by $url", e)
                    emptyList<News>()
                }

            }.flatten()
        }
    }

    private fun toNews(it: Item?): News = News(
            id = DigestUtils.md5Hex(it?.guid?.get().toString()),
            date = try {
                LocalDate.parse(it?.pubDate?.get(), dateFormatter)
            } catch (e: Exception) {
                LocalDate.now()
            },
            text = it?.title?.get().toString(),
            link = it?.link?.get().toString(),
            source = "RSS"
    )
}