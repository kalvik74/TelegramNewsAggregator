package com.java993.telegramBotNewsAggregator.behaviour.model

data class User (
        val id: Long,
        val chatId: Long,
        val name: String,
        val settings: Setting
)