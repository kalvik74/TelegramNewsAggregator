package com.java993.telegramBotNewsAggregator.scheduler.searcher

import com.java993.telegramBotNewsAggregator.scheduler.searcher.dto.News

interface Searcher {
    fun getNews(userId: Long): List<News>
}