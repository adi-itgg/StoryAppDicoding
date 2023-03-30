package me.syahdilla.putra.sholeh.story.core.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.data.User
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.StoriesResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.StoryResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.UserResponse
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import java.io.File

interface StoryRepository {

    suspend fun login(email: String, password: String): Result<UserResponse>

    suspend fun register(name: String, email: String, password: String): Result<UserResponse>

    suspend fun getStories(token: String, withLocation: Boolean, page: Int?, size: Int?): Result<StoriesResponse>
    suspend fun getStories(user: User, withLocation: Boolean, page: Int? = null, size: Int? = null): Result<StoriesResponse> = getStories(user.token, withLocation, page, size)

    suspend fun getStoryDetails(id: String, token: String): Result<StoryResponse>

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<ApiBasicResponse>

    suspend fun createStory(token: String, description: String, photo: File): Result<ApiBasicResponse> = createStory(token, description, photo, null, null)

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<ApiBasicResponse>?

    fun getStoriesMediator(): Flow<PagingData<StoryEntity>>

}