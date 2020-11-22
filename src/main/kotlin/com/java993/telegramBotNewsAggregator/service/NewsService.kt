package com.java993.telegramBotNewsAggregator.service

import com.java993.telegramBotNewsAggregator.scheduler.searcher.dto.News

interface NewsService {
    fun isNewsAlreadyExist(id: String): Boolean
    fun create(news: News)
}