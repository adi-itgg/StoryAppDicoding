package me.syahdilla.putra.sholeh.story.core.data.source.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.syahdilla.putra.sholeh.story.core.domain.model.User

@Serializable
data class UserResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("loginResult")
    val loginResult: User? = null,
    @SerialName("message")
    val message: String
)