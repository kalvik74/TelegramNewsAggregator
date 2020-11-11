package com.java993.telegramBotNewsAggregator.utils

import java.net.URL


class UrlUtils {
    companion object UrlUtils {
        fun isValidURL(url: String?): Boolean {
            try {
                URL(url).toURI()
            } catch (e: Exception) {
                return false
            }
            return true
        }

    }
}