package me.syahdilla.putra.sholeh.story.core.data

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManager
import me.syahdilla.putra.sholeh.story.core.data.source.remote.StoryRemoteMediator
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.asObject
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import me.syahdilla.putra.sholeh.story.core.utils.wrapEspressoIdlingResource
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
        api.login(me.syahdilla.putra.sholeh.story.core.data.UserLogin(email, password)).awaitBody()
    }


    override suspend fun register(name: String, email: String, password: String) = wrapEspressoIdlingResource {
        api.register(name, email, password).awaitBody()
    }

    override suspend fun getStories(token: String, withLocation: Boolean, page: Int?, size: Int?) = api.getAllStories(token.asBearerToken, if (withLocation) 1 else null, page, size).awaitBody()
    override suspend fun getStories(user: me.syahdilla.putra.sholeh.story.core.data.User, withLocation: Boolean, page: Int?, size: Int?) = getStories(user.token, withLocation, page, size)

    override suspend fun getStoryDetails(id: String, token: String) = api.getStoryDetails(id, token.asBearerToken).awaitBody()

    override suspend fun createStory(token: String, description: String, photo: File, lat: Float?, lon: Float?): Result<ApiBasicResponse> {
        val uploadDescription = description.toRequestBody(MultipartBody.FORM)
        val requestPhoto = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val uploadPhoto = MultipartBody.Part.createFormData("photo", photo.name, requestPhoto)
        return api.createStory(token.asBearerToken, uploadDescription, uploadPhoto, lat, lon).awaitBody()
    }

    override suspend fun createStory(imageManager: ImageManager, token: String, description: String, photo: Uri, lat: Float?, lon: Float?) = withContext(IO) {
        val file = imageManager.uriToFile(photo) ?: return@withContext null
        createStory(token, description, file, lat, lon)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesMediator() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = mediator,
        pagingSourceFactory = { database.getAllStories() }
    ).flow

}