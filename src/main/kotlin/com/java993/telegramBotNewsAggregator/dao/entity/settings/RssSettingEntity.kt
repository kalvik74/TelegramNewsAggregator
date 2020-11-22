package com.java993.telegramBotNewsAggregator.dao.entity.settings

data class RssSettingEntity (
        val url: List<String> = listOf(),
        val searchFilters: List<String> = listOf()
)
