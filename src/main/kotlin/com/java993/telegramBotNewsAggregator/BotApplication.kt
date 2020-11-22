package com.java993.telegramBotNewsAggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableRedisRepositories(basePackages = ["com.java993"])
@SpringBootApplication
@EnableScheduling
class BotApplication

fun main(args: Array<String>) {
    runApplication<BotApplication>(*args)
}