package com.java993.telegramBotNewsAggregator.dao.entity

import com.java993.telegramBotNewsAggregator.dao.entity.settings.SettingEntity
import javax.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("USERS")
data class UserEntity(
        @Id
        val id: Long,
        val chatId: Long,
        val name: String,
        val settings: SettingEntity
)