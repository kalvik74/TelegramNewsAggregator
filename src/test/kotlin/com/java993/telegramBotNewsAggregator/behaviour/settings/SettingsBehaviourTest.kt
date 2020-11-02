package com.java993.telegramBotNewsAggregator.behaviour.settings

import com.java993.telegramBotNewsAggregator.createFrom
import com.java993.telegramBotNewsAggregator.createMessage
import com.java993.telegramBotNewsAggregator.createModelUser
import com.java993.telegramBotNewsAggregator.createUpdate
import com.java993.telegramBotNewsAggregator.service.UserService
import com.nhaarman.mockitokotlin2.*
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class SettingsBehaviourTest {

    @Mock
    private lateinit var telegramSender: TelegramSender

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var settingsBehaviour: SettingsBehaviour

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `WHEN send bad command THEN no answer`() {
        settingsBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/badCommand")
                )
        )

        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send settings command but from is null THEN no answer`() {
        settingsBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/settings")
                )
        )
        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send settings command THEN success answer`() {
        val messageCaptor = argumentCaptor<SendMessageRequest>()
        whenever(userService.updateUserInfoAndGet(any(), any(), any())).thenReturn(createModelUser())

        settingsBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/settings",
                                from = createFrom(firstName = "")
                        )
                )
        )
        verify(telegramSender, times(1)).executeMethod(messageCaptor.capture())
        assertTrue(messageCaptor.lastValue.text.contains("your settings: "))
        assertEquals("1", messageCaptor.lastValue.chatId)
    }

}