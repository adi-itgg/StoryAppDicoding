package me.syahdilla.putra.sholeh.story.core.data

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManager
import me.syahdilla.putra.sholeh.story.core.data.source.remote.StoryRemoteMediator
import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.domain.model.UserLogin
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.*
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.core.annotation.Single
import retrofit2.Call
import java.io.File

@Single
class StoryRepositoryImpl(
    private val retrofitManager: RetrofitManager,
    private val mediator: StoryRemoteMediator,
    private val database: StoryDatabase
): StoryRepository {

    private val logger by customLogger()

    private val api
        get() = retrofitManager.api

    private val String.asBearerToken
        get() = "Bearer $this"

    private suspend inline fun<reified T: Any> Call<T>.awaitBody(): Result<T> = withContext(IO) {
        tryRun(noLogs = false) {
            val res = execute()
            logger.debug("HTTP Response: ${res.code()}", res.message())
            res.body() ?: res.errorBody()?.string()?.asObject() ?: throw NullPointerException("Body is null!")
        }
    }

    override suspend fun login(email: String, password: String) = wrapEspressoIdlingResource {
        tryRun {
            api.login(UserLogin(email, password)).awaitBody().getOrThrow().loginResult as User
        }
    }


    override suspend fun register(name: String, email: String, password: String) = wrapEspressoIdlingResource {
        tryRun {
            val res = api.register(name, email, password).awaitBody().getOrThrow()
            if (res.error) {
                throw IllegalArgumentException(res.message)
            }
            res.loginResult as User
        }
    }

    override suspend fun getStories(token: String, withLocation: Boolean, page: Int?, size: Int?) = tryRun {
        api.getAllStories(token.asBearerToken, if (withLocation) 1 else null, page, size).awaitBody().getOrThrow().listStory
    }
    override suspend fun getStories(user: User, withLocation: Boolean, page: Int?, size: Int?) = getStories(user.token, withLocation, page, size)

    override suspend fun getStoryDetails(id: String, token: String) = tryRun {
        api.getStoryDetails(id, token.asBearerToken).awaitBody().getOrThrow().story
    }

    override suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<String> {
        val uploadDescription = description.toRequestBody(MultipartBody.FORM)
        val requestPhoto = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val uploadPhoto = MultipartBody.Part.createFormData("photo", photo.name, requestPhoto)
        return tryRun {
            api.createStory(token.asBearerToken, uploadDescription, uploadPhoto, lat, lon).awaitBody().getOrThrow().message
        }
    }

    override suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?) = withContext(IO) {
        val file = imageManager.uriToFile(photo) as File
        createStory(token, description, file, lat, lon)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesMediator() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = mediator,
        pagingSourceFactory = { database.getAllStories() }
    ).flow.map { paging -> paging.map { DataMapper.mapEntityToDomain(it) } }

}