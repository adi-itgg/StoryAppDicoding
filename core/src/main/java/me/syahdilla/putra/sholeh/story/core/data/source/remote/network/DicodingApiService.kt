package me.syahdilla.putra.sholeh.story.core.data.source.remote.network

import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.ApiBasicResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.StoriesResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.StoryResponse
import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DicodingApiService {

    @POST("login")
    fun login(
        @Body userLogin: me.syahdilla.putra.sholeh.story.core.data.UserLogin
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Call<StoriesResponse>

    @GET("stories/{id}")
    fun getStoryDetails(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<StoryResponse>

    @POST("stories")
    @Multipart
    fun createStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null
    ): Call<ApiBasicResponse>

}