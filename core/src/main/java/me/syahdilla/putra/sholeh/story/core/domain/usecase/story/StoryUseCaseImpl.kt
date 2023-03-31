package me.syahdilla.putra.sholeh.story.core.domain.usecase.story

import android.net.Uri
import androidx.paging.map
import kotlinx.coroutines.flow.map
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.DataMapper
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
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
    ) = tryRun {
        repository.createStory(token, description, photo, lat, lon).getOrThrow().message
    }

    override suspend fun createStory(
        imageManager: ImageManager,
        token: String,
        description: String,
        photo: Uri,
        lat: Float?,
        lon: Float?
    ) = tryRun {
        repository.createStory(imageManager, token, description, photo, lat, lon).getOrThrow().message
    }

    override fun getStoriesMediator() = repository.getStoriesMediator().map { paging -> paging.map { DataMapper.mapEntityToDomain(it) } }
    override suspend fun getStories(user: User, withLocation: Boolean, page: Int?, size: Int?) = tryRun {
        repository.getStories(user, withLocation, page, size).getOrThrow().listStory
    }

}