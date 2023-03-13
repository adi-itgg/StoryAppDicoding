package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story

@Serializable
data class StoryResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("story")
    val story: Story
)