package com.java993.telegramBotNewsAggregator.behaviour.model


data class Setting (
        val enableNotification: Boolean = true,
        val rss: RssSetting,
        val reddit: RedditSetting
)
