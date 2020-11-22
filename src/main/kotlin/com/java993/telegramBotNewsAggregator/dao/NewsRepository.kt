package com.java993.telegramBotNewsAggregator.dao

import com.java993.telegramBotNewsAggregator.dao.entity.NewsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository : CrudRepository<NewsEntity, String?>
