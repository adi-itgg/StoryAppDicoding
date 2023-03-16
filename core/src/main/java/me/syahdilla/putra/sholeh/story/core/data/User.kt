package me.syahdilla.putra.sholeh.story.core.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("name")
    val name: String,
    @SerialName("token")
    val token: String,
    @SerialName("userId")
    val userId: String
)