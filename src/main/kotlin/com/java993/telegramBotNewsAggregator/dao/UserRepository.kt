package com.java993.telegramBotNewsAggregator.dao

import com.java993.telegramBotNewsAggregator.dao.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long?>
