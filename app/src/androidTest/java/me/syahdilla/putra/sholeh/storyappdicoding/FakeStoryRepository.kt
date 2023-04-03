package me.syahdilla.putra.sholeh.storyappdicoding

import android.net.Uri
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import java.io.File

class FakeStoryRepository: StoryRepository {

    private val err = Exception("Testing")

    override suspend fun login(email: String, password: String): Result<User> = Result.failure(err)

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<User> = Result.failure(err)

    override suspend fun getStories(
        token: String,
        withLocation: Boolean,
        page: Int?,
        size: Int?
    ): Result<List<Story>> {
        val items: MutableList<Story> = mutableListOf()
        for (i in 0..100) {
            val quote = Story(
                i.toString(),
                "aaa $i",
                description = "desc $i",
                name = "name $i",
                photoUrl = "poto $i"
            )
            items.add(quote)
        }
        return Result.success(items)
    }

    override suspend fun getStoryDetails(id: String, token: String): Result<Story> = Result.failure(err)

    override suspend fun createStory(
        token: String,
        description: String,
        photo: File,
        lat: Float?,
        lon: Float?
    ): Result<String> = Result.failure(err)

    override suspend fun createStory(
        imageManager: ImageManager,
        token: String,
        description: String,
        photo: Uri,
        lat: Float?,
        lon: Float?
    ): Result<String> = Result.failure(err)

    override fun getStoriesMediator(): Flow<PagingData<Story>> = flowOf()


}