package com.java993.telegramBotNewsAggregator.behaviour

import com.java993.telegramBotNewsAggregator.createFrom
import com.java993.telegramBotNewsAggregator.createMessage
import com.java993.telegramBotNewsAggregator.createModelUser
import com.java993.telegramBotNewsAggregator.createUpdate
import com.java993.telegramBotNewsAggregator.service.UserService
import org.artfable.telegram.api.request.SendMessageRequest
import org.artfable.telegram.api.service.TelegramSender
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.nhaarman.mockitokotlin2.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import com.java993.telegramBotNewsAggregator.behaviour.model.User as UserModel


internal class RssSettingsUrlAddBehaviourTest {


    @Mock
    private lateinit var telegramSender: TelegramSender;

    @Mock
    private lateinit var userService: UserService;


    @InjectMocks
    lateinit var rssSettingsUrlAddBehaviour: RssSettingsUrlAddBehaviour

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `WHEN send bad command THEN no answer`() {
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/badCommand")
                )
        )

        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send newrssurl command but from is null THEN no answer`() {
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/newrssurl")
                )
        )
        verify(telegramSender, times(0)).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN send newrssurl command THEN success answer`() {
        val messageCaptor = argumentCaptor<SendMessageRequest>()

        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/newrssurl",
                                from = createFrom(firstName = "")
                        )
                )
        )
        verify(telegramSender, times(1)).executeMethod(messageCaptor.capture())
        assertEquals("please enter rss url (example: https://habr.com/ru/rss/)", messageCaptor.lastValue.text)
        assertEquals("1", messageCaptor.lastValue.chatId)
    }

    @Test
    fun `WHEN enter new invalid url THEN error answer`() {


        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/newrssurl",
                                from = createFrom(firstName = "")
                        )
                )
        )
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "bad url",
                                from = createFrom(firstName = "")
                        )
                )
        )
        val messageCaptor = argumentCaptor<SendMessageRequest>()
        verify(telegramSender, times(2)).executeMethod(messageCaptor.capture())
        assertEquals("Url bad url has mistakes, try again", messageCaptor.lastValue.text)
        assertEquals("1", messageCaptor.lastValue.chatId)
    }

    @Test
    fun `WHEN enter new valid url THEN error answer`() {
        val messageCaptor = argumentCaptor<SendMessageRequest>()

        val userCaptor = argumentCaptor<UserModel>()
        whenever(userService
                .updateUserInfoAndGet(any(), any(), any())
        ).thenReturn(
                createModelUser()
        )

        //send command for set inner state
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/newrssurl",
                                from = createFrom(firstName = "")
                        )
                )
        )

        //send new url for saving
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "http://habra.com",
                                from = createFrom(firstName = "")
                        )
                )
        )


        verify(telegramSender, times(2)).executeMethod(messageCaptor.capture())
        verify(userService, times(1)).save(userCaptor.capture());
        assertEquals("Url http://habra.com added successfully", messageCaptor.lastValue.text)
        assertEquals("1", messageCaptor.lastValue.chatId)
        assertEquals(1, userCaptor.lastValue.chatId)
        assertEquals(1, userCaptor.lastValue.id)
    }


}