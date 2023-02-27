package me.syahdilla.putra.sholeh.storyappdicoding.network

import android.net.Uri
import me.syahdilla.putra.sholeh.storyappdicoding.data.*
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.ImageManager
import java.io.File

interface ApiRepository {

    suspend fun login(email: String, password: String): Result<UserResponse>

    suspend fun register(name: String, email: String, password: String): Result<UserResponse>

    suspend fun getStories(token: String, withLocation: Boolean, page: Int?, size: Int?): Result<StoriesResponse>
    suspend fun getStories(user: User, withLocation: Boolean, page: Int? = null, size: Int? = null): Result<StoriesResponse> = getStories(user.token, withLocation, page, size)

    suspend fun getStoryDetails(id: String, token: String): Result<StoryResponse>

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<ApiBasicResponse>

    suspend fun createStory(token: String, description: String, photo: File): Result<ApiBasicResponse> = createStory(token, description, photo, null, null)

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<ApiBasicResponse>?

}