package me.syahdilla.putra.sholeh.story.core.data.source.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.syahdilla.putra.sholeh.story.core.BuildConfig
import me.syahdilla.putra.sholeh.story.core.data.source.remote.network.DicodingApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
class RetrofitManagerImpl(
    private val client: OkHttpClient
): RetrofitManager {

    override val retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    override val api: DicodingApiService by lazy {
        retrofit.create(DicodingApiService::class.java)
    }

}