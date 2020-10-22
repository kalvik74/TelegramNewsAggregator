package com.java993.telegramBotNewsAggregator.dao.entity

data class RedditSettingEntity (
        val communities: List<String> = listOf(),
        val searchFilter: List<String> = listOf()
)
