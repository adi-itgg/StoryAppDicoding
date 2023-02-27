package me.syahdilla.putra.sholeh.storyappdicoding

import android.net.Uri
import me.syahdilla.putra.sholeh.storyappdicoding.data.*
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.ImageManager
import java.io.File

class FakeApiRepository: ApiRepository {

    private val err = Exception("Testing")

    override suspend fun login(email: String, password: String): Result<UserResponse> = Result.failure(err)

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<UserResponse> = Result.failure(err)

    override suspend fun getStories(
        token: String,
        withLocation: Boolean,
        page: Int?,
        size: Int?
    ): Result<StoriesResponse> {
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
        return Result.success(StoriesResponse(false, items, ""))
    }

    override suspend fun getStoryDetails(id: String, token: String): Result<StoryResponse> = Result.failure(err)

    override suspend fun createStory(
        token: String,
        description: String,
        photo: File,
        lat: Float?,
        lon: Float?
    ): Result<ApiBasicResponse> = Result.failure(err)

    override suspend fun createStory(
        imageManager: ImageManager,
        token: String,
        description: String,
        photo: Uri,
        lat: Float?,
        lon: Float?
    ): Result<ApiBasicResponse>? = null


}