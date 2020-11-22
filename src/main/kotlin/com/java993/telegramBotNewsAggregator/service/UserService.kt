package com.java993.telegramBotNewsAggregator.service

import com.java993.telegramBotNewsAggregator.behaviour.model.User
import java.util.*

interface UserService {
    fun getUserById(userId: Long): Optional<User>
    fun updateUserInfoAndGet(userId: Long, userName: String, chatId: Long): User
    fun getAllUsers(): List<User>
    fun getAllUsersWithEnabledNotification(): List<User>
    fun save(user: User)
    fun disableNotification(userId: Long)
    fun enableNotification(userId: Long)
}