package com.java993.telegramBotNewsAggregator.behaviour

import com.java993.telegramBotNewsAggregator.service.UserService
import org.artfable.telegram.api.*
import org.artfable.telegram.api.request.SendMessageRequest

import org.artfable.telegram.api.service.TelegramSender
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class RssSettingsUrlAddBehaviourTest {


    @Mock
    private lateinit var telegramSender: TelegramSender;

    @Mock
    private lateinit var userService: UserService;

    @InjectMocks
    lateinit var rssSettingsUrlAddBehaviour: RssSettingsUrlAddBehaviour;

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun `WHEN bad command THEN no answer`() {
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/badCommand")
                )
        )

        verify(telegramSender, never()).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN newrssurl but from is null THEN no answer`() {
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(text = "/newrssurl")
                )
        )
        verify(telegramSender, never()).executeMethod(any<SendMessageRequest>())
    }

    @Test
    fun `WHEN newrssurl THEN success answer`() {
        rssSettingsUrlAddBehaviour.parse(
                update = createUpdate(
                        message = createMessage(
                                text = "/newrssurl",
                                from = createFrom()
                        )
                )
        )
        verify(telegramSender, Mockito.only()).executeMethod(any<SendMessageRequest>())
    }

    private fun createFrom(id: Long = 1, firstName: String = ""): User? = User(
            id = id,
            firstName = firstName
    )




    fun createUpdate(
            id: Long = 1, message: Message): Update = Update(
            updateId = id,
            message = message
    )


    private fun createMessage(messageId: Long = 1, text: String = "", chatId: Long = 1, from: User? = null): Message = Message(
            messageId = messageId,
            date = 1,
            text = text,
            chat = Chat(
                    id = chatId,
                    type = ChatType.CHANNEL
            ),
            from = from
    )

}