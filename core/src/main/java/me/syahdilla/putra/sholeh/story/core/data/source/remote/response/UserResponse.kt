package me.syahdilla.putra.sholeh.story.core.data.source.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.syahdilla.putra.sholeh.story.core.data.User

@Serializable
data class UserResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("loginResult")
    val loginResult: me.syahdilla.putra.sholeh.story.core.data.User? = null,
    @SerialName("message")
    val message: String
)