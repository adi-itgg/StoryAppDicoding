package me.syahdilla.putra.sholeh.story.core.domain.usecase.story

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import java.io.File


interface StoryUseCase {

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<String>

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<String>

    fun getStoriesMediator(): Flow<PagingData<Story>>

    suspend fun getStories(user: User, withLocation: Boolean, page: Int? = null, size: Int? = null): Result<List<Story>>

}