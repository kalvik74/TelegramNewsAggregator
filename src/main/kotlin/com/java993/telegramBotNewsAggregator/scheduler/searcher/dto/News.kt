package com.java993.telegramBotNewsAggregator.scheduler.searcher.dto

import java.time.LocalDate
import java.util.*

data class News (val id: String, val text: String, val link: String, val date: LocalDate, val source: String)
