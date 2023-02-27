package me.syahdilla.putra.sholeh.storyappdicoding.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("story")
    val story: Story
)