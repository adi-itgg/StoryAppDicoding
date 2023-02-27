package me.syahdilla.putra.sholeh.storyappdicoding.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Stories response
 * https://story-api.dicoding.dev/v1/stories?location=1
 */
@Serializable
data class StoriesResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("listStory")
    val listStory: List<Story>,
    @SerialName("message")
    val message: String
)