package com.java993.telegramBotNewsAggregator.service.impl

import com.java993.telegramBotNewsAggregator.service.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceImplTest @Autowired constructor(
        val userService: UserService) {

    @Test
    fun `When updateUserInfoAndGet then return User`() {
        val user = userService.updateUserInfoAndGet(1, "user1", 1)

        Assertions.assertThat(user.id).isEqualTo(1)
        Assertions.assertThat(user.name).isEqualTo("user1")
        Assertions.assertThat(user.chatId).isEqualTo(1)
    }


    @Test
    fun `When getUserById then return User`() {
        Assertions.assertThat(userService.getUserById(2).isEmpty).isTrue

        userService.updateUserInfoAndGet(2, "user1", 1)

        val user = userService.getUserById(2)

        Assertions.assertThat(user.get().id).isEqualTo(2)
        Assertions.assertThat(user.get().name).isEqualTo("user1")
        Assertions.assertThat(user.get().chatId).isEqualTo(1)
    }

    @Test
    fun `When getAllUsers then return UserList`() {
        val sizeBefore = userService.getAllUsers().size
        userService.updateUserInfoAndGet(3, "user1", 3)

        Assertions.assertThat(userService.getAllUsers().size).isEqualTo(sizeBefore + 1)
    }

    @Test
    fun `When enableNotification then return success`() {
        var user = userService.updateUserInfoAndGet(4, "user1", 4)
        Assertions.assertThat(user.settings.enableNotification).isEqualTo(false)

        userService.enableNotification(user.id)

        user = userService.getUserById(user.id).get()
        Assertions.assertThat(user.settings.enableNotification).isEqualTo(true)
    }

    @Test
    fun `When disableNotification then return success`() {
        var user = userService.updateUserInfoAndGet(5, "user1", 5)
        Assertions.assertThat(user.settings.enableNotification).isEqualTo(false)

        userService.disableNotification(user.id)

        user = userService.getUserById(user.id).get()
        Assertions.assertThat(user.settings.enableNotification).isEqualTo(false)
    }
}