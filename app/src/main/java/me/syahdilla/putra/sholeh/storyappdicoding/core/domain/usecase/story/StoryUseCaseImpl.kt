package me.syahdilla.putra.sholeh.storyappdicoding.core.domain.usecase.story

import android.net.Uri
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.storyappdicoding.utils.image.ImageManager
import org.koin.core.annotation.Factory
import java.io.File


@Factory
internal class StoryUseCaseImpl(
    private val repository: StoryRepository
): StoryUseCase {

    override fun getStoriesMediatorFlow() = repository.getStoriesMediatorFlow()

    override suspend fun createStory(
        token: String,
        description: String,
        photo: File,
        lat: Float?,
        lon: Float?
    ): Result<ApiBasicResponse> = repository.createStory(token, description, photo, lat, lon)

    override suspend fun createStory(
        imageManager: ImageManager,
        token: String,
        description: String,
        photo: Uri,
        lat: Float?,
        lon: Float?
    ): Result<ApiBasicResponse>? = repository.createStory(imageManager, token, description, photo, lat, lon)

}