package com.java993.telegramBotNewsAggregator.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Profile("local")
@Configuration
class LocalRedisConfiguration {
    private val redisServer: RedisServer = RedisServer()

    @PostConstruct
    fun postConstruct() {
        redisServer.start()
    }

    @PreDestroy
    fun preDestroy() {
        redisServer.stop()
    }
}
