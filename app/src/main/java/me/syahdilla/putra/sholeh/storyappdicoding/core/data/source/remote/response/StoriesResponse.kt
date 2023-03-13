package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story

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