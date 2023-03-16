package me.syahdilla.putra.sholeh.story.core.data.source.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiBasicResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String
)