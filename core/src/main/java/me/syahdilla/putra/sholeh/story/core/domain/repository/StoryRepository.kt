package me.syahdilla.putra.sholeh.story.core.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import java.io.File

interface StoryRepository {

    suspend fun login(email: String, password: String): Result<User>

    suspend fun register(name: String, email: String, password: String): Result<Boolean>

    suspend fun getStories(token: String, withLocation: Boolean, page: Int?, size: Int?): Result<List<Story>>
    suspend fun getStories(user: User, withLocation: Boolean, page: Int? = null, size: Int? = null): Result<List<Story>> = getStories(user.token, withLocation, page, size)

    suspend fun getStoryDetails(id: String, token: String): Result<Story>

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<String>

    suspend fun createStory(token: String, description: String, photo: File): Result<String> = createStory(token, description, photo, null, null)

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<String>

    fun getStoriesMediator(): Flow<PagingData<Story>>

}