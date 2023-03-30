package me.syahdilla.putra.sholeh.story.core.domain.usecase.story

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.data.User
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.StoriesResponse
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import java.io.File


interface StoryUseCase {

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<ApiBasicResponse>

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<ApiBasicResponse>?

    fun getStoriesMediator(): Flow<PagingData<StoryEntity>>

    suspend fun getStories(user: User, withLocation: Boolean, page: Int? = null, size: Int? = null): Result<StoriesResponse>

}