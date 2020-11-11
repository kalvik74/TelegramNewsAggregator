package com.java993.telegramBotNewsAggregator

import org.artfable.telegram.api.Behaviour
import org.artfable.telegram.api.CallbackBehaviour
import org.artfable.telegram.api.LongPollingTelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TelegramBotNewsAggregator @Autowired constructor(
        notificationBehaviour: Behaviour,
        settingsBehaviour: Behaviour,
        rssSettingsUrlAddBehaviour: Behaviour,
        rssSettingsUrlDeleteBehaviour: Behaviour,
        rssSettingsUrlDeleteButtonBehaviour: CallbackBehaviour,
        rssSettingsFilterAddBehaviour: Behaviour,
        rssSettingsFilterDeleteBehaviour: Behaviour,
        rssSettingsFilterDeleteButtonBehaviour: CallbackBehaviour
)
    : LongPollingTelegramBot(
        mutableSetOf(notificationBehaviour, settingsBehaviour, rssSettingsUrlAddBehaviour, rssSettingsUrlDeleteBehaviour, rssSettingsFilterAddBehaviour, rssSettingsFilterDeleteBehaviour),
        setOf(rssSettingsUrlDeleteButtonBehaviour, rssSettingsFilterDeleteButtonBehaviour)
) {
}