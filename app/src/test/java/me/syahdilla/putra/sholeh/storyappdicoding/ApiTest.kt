@file:OptIn(ExperimentalCoroutinesApi::class)

package me.syahdilla.putra.sholeh.storyappdicoding

import androidx.paging.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.storyappdicoding.utils.KoinTesting
import me.syahdilla.putra.sholeh.story.core.utils.asJson
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import org.koin.test.inject
import java.io.File
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApiTest: KoinTesting {

    private val logger by customLogger()

    private val repository: StoryRepository by inject()
    private val userTestSession by lazy {
        me.syahdilla.putra.sholeh.story.core.data.User(
            name = "User Test 1",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXZIRHFMMFVqeDVyUE5OVTgiLCJpYXQiOjE2NzMyNjk3NjB9.GwU3zTkK6ztHULhw26fIykAq2Ij6n0Hgo081aRV-zBo",
            userId = "user-vHDqL0Ujx5rPNNU8"
        )
    }
    private val userTest = "user1.test@gmail.com" to "12345678"
    private val classLoader by lazy {
        Thread.currentThread().contextClassLoader
    }

    @Test
    fun `test user login`() = runTest {
        val response = repository.login(
            email = userTest.first,
            password = userTest.second).getOrThrow()
        if (response.error)
            return@runTest logger.error("Login gagal:", response.message)
        logger.debug("Login", response.message)
    }

    @Test
    fun `test user register`() = runTest {
        val response = repository.register(
            name = "User Test 1",
            email = userTest.first,
            password = userTest.second
        ).getOrThrow()
        assertEquals(response.message, "Email is already taken")
    }

    @Test
    fun `test get all user stories`() = runTest {
        val response = repository.getStories(
            token = userTestSession.token,
            withLocation = false,
            page = null,
            size = null
        ).getOrThrow()
        assertTrue { !response.error }
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

    @Test
    fun `test get story details`() = runTest {
        val response = repository.getStoryDetails(
            id = "story-h88og1LDkqfb7bb2",
            token = userTestSession.token
        ).getOrThrow()
        assertTrue { !response.error }
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

    @Test
    fun `test create story`() = runTest {
        val response = repository.createStory(
            token = userTestSession.token,
            description = "test ${Random.nextLong(Int.MAX_VALUE.toLong())}",
            File(classLoader.getResource("man.png").file)
        ).getOrThrow()

        logger.debug { "result isError: ${response.error} - message: ${response.message}" }
        assertTrue { !response.error }
    }

    @Test
    fun `test get story paging`() = runTest {
        val response = repository.getStories(
            token = userTestSession.token,
            withLocation = true,
            page = 1,
            size = 10
        ).getOrThrow()
        assertTrue { !response.error }
        assertTrue { response.listStory.isNotEmpty() }
        logger.debug("Success with result:\n", response.asJson(prettyPrint = true))
    }

}