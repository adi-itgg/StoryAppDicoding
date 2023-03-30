package me.syahdilla.putra.sholeh.story.core.domain.usecase.story

import android.net.Uri
import me.syahdilla.putra.sholeh.story.core.data.User
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import org.koin.core.annotation.Factory
import java.io.File


@Factory
internal class StoryUseCaseImpl(
    private val repository: StoryRepository
): StoryUseCase {

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

    override fun getStoriesMediator() = repository.getStoriesMediator()
    override suspend fun getStories(user: User, withLocation: Boolean, page: Int?, size: Int?) = repository.getStories(user, withLocation, page, size)

}