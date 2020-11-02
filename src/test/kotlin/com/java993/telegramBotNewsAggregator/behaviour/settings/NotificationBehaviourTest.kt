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

internal class NotificationBehaviourTest {

    @Mock
    private lateinit var telegramSender: TelegramSender

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var notificationBehaviour: NotificationBehaviour

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `WHEN send bad command THEN no answer`() {
        notificationBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/badCommand")
                )
        )

        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send start command but from is null THEN no answer`() {
        notificationBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/start")
                )
        )
        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send start command THEN success answer`() {
        val messageCaptor = argumentCaptor<SendMessageRequest>()
        whenever(userService.updateUserInfoAndGet(any(), any(), any())).thenReturn(createModelUser())

        notificationBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/start",
                                from = createFrom(firstName = "")
                        )
                )
        )
        verify(telegramSender, times(1)).executeMethod(messageCaptor.capture())
        verify(userService, times(1)).enableNotification(eq(1))
        assertEquals("news notification enabled", messageCaptor.lastValue.text)
        assertEquals("1", messageCaptor.lastValue.chatId)
    }

    @Test
    fun `WHEN send stop command THEN success answer`() {
        val messageCaptor = argumentCaptor<SendMessageRequest>()
        whenever(userService.updateUserInfoAndGet(any(), any(), any())).thenReturn(createModelUser())

        notificationBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/stop",
                                from = createFrom(firstName = "")
                        )
                )
        )
        verify(telegramSender, times(1)).executeMethod(messageCaptor.capture())
        verify(userService, times(1)).disableNotification(eq(1))
        assertEquals("news notification disabled", messageCaptor.lastValue.text)
        assertEquals("1", messageCaptor.lastValue.chatId)
    }
}