package me.syahdilla.putra.sholeh.storyappdicoding.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("loginResult")
    val loginResult: User? = null,
    @SerialName("message")
    val message: String
)