package com.java993.telegramBotNewsAggregator.utils

import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL


class UrlUtils {
    companion object UrlUtils {
        fun isValidURL(url: String?): Boolean {
            try {
                URL(url).toURI()
            } catch (e: MalformedURLException) {
                return false
            } catch (e: URISyntaxException) {
                return false
            }
            return true
        }

    }
}