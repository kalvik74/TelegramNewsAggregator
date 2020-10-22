package com.java993.telegramBotNewsAggregator.dao.entity

data class SettingEntity (
        val enableNotification: Boolean,
        val rss: RssSettingEntity = RssSettingEntity(ArrayList(), ArrayList()),
        val reddit: RedditSettingEntity = RedditSettingEntity(ArrayList(), ArrayList())
)
