package com.java993.telegramBotNewsAggregator.dao.entity

data class RssSettingEntity (
        val url: List<String> = listOf(),
        val searchFilters: List<String> = listOf()
)
