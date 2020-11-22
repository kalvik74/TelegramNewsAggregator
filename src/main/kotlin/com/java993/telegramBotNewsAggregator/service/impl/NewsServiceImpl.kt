package com.java993.telegramBotNewsAggregator.service.impl

import com.java993.telegramBotNewsAggregator.dao.NewsRepository
import com.java993.telegramBotNewsAggregator.dao.entity.NewsEntity
import com.java993.telegramBotNewsAggregator.scheduler.searcher.dto.News
import com.java993.telegramBotNewsAggregator.service.NewsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsServiceImpl : NewsService {

    @Autowired
    private lateinit var newsRepository: NewsRepository

    override fun isNewsAlreadyExist(id: String): Boolean = newsRepository.findById(id).isPresent

    override fun create(news: News) {
        newsRepository.save(toNewsEntity(news))
    }

    private fun toNewsEntity(news: News): NewsEntity = NewsEntity(id = news.id, date = news.date)
}