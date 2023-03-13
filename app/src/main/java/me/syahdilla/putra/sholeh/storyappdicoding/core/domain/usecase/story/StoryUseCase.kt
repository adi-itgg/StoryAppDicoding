package me.syahdilla.putra.sholeh.storyappdicoding.core.domain.usecase.story

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.storyappdicoding.utils.image.ImageManager
import java.io.File


interface StoryUseCase {

    fun getStoriesMediatorFlow(): Flow<PagingData<StoryEntity>>

    suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<ApiBasicResponse>

    suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?): Result<ApiBasicResponse>?

}