package com.java993.telegramBotNewsAggregator.dao.entity

import org.springframework.data.redis.core.RedisHash
import java.time.LocalDate
import javax.persistence.Id

@RedisHash("NEWS")
data class NewsEntity(@Id val id: String, val date: LocalDate)