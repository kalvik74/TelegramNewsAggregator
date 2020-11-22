package com.java993.telegramBotNewsAggregator.dao.entity.settings

data class RedditSettingEntity (
        val communities: List<String> = listOf(),
        val searchFilter: List<String> = listOf()
)
