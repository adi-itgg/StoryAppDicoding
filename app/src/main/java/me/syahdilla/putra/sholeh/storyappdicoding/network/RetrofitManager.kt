package me.syahdilla.putra.sholeh.storyappdicoding.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import me.syahdilla.putra.sholeh.storyappdicoding.BuildConfig
import me.syahdilla.putra.sholeh.storyappdicoding.network.api.DicodingApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Retrofit manager
 *
 * @property baseUrl put manually for testing
 */
@OptIn(ExperimentalSerializationApi::class)
class RetrofitManager(
    private val baseUrl: String? = null,
    private val client: OkHttpClient
) {

    private val retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(baseUrl ?: BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    val api by lazy {
        retrofit.create(DicodingApi::class.java)
    }

}