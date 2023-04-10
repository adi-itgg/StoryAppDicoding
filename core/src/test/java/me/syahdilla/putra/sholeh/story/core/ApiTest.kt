@file:OptIn(ExperimentalCoroutinesApi::class)

package me.syahdilla.putra.sholeh.story.core

import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.KoinTesting
import me.syahdilla.putra.sholeh.story.core.utils.asJson
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import org.koin.test.inject
import java.io.File
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class ApiTest: KoinTesting {

    private val logger by customLogger()

    private val repository: StoryRepository by inject()
    private val userTest = "user1.test@gmail.com" to "12345678"
    private val classLoader by lazy {
        Thread.currentThread().contextClassLoader
    }

    suspend fun userLogin(): User = repository.login(
        email = userTest.first,
        password = userTest.second
    ).getOrThrow()

    @Test
    fun `test user login`() = runTest {
        val response = repository.login(
            email = userTest.first,
            password = userTest.second
        ).getOrThrow()

        logger.debug("Login", response.name)
        logger.debug("token", response.token)
    }

    @Test
    fun `test user register`() = runTest {
        repository.register(
            name = "User Test 1",
            email = userTest.first,
            password = userTest.second
        ).getOrElse {
            logger.debug("message", it.message)
        }
    }

    @Test
    fun `test get all user stories`() = runTest {
        val response = repository.getStories(
            token = userLogin().token,
            withLocation = false,
            page = null,
            size = null
        ).getOrThrow()
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

    @Test
    fun `test get story details`() = runTest {
        val response = repository.getStoryDetails(
            id = "story-h88og1LDkqfb7bb2",
            token = userLogin().token
        ).getOrThrow()
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

    @Test
    fun `test create story`() = runTest {
        repository.createStory(
            token = userLogin().token,
            description = "test ${Random.nextLong(Int.MAX_VALUE.toLong())}",
            File(classLoader.getResource("man.png").file)
        ).getOrThrow()
    }

    @Test
    fun `test get story paging`() = runTest {
        val response = repository.getStories(
            token = userLogin().token,
            withLocation = true,
            page = 1,
            size = 10
        ).getOrThrow()
        assertTrue { response.isNotEmpty() }
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

}