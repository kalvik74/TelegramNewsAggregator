package com.java993.telegramBotNewsAggregator.dao.entity.settings

import com.java993.telegramBotNewsAggregator.dao.entity.settings.RedditSettingEntity
import com.java993.telegramBotNewsAggregator.dao.entity.settings.RssSettingEntity

data class SettingEntity (
        val enableNotification: Boolean,
        val rss: RssSettingEntity = RssSettingEntity(ArrayList(), ArrayList()),
        val reddit: RedditSettingEntity = RedditSettingEntity(ArrayList(), ArrayList())
)
