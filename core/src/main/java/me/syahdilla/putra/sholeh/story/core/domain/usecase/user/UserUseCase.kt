package me.syahdilla.putra.sholeh.story.core.domain.usecase.user

import me.syahdilla.putra.sholeh.story.core.data.source.remote.response.UserResponse

interface UserUseCase {

    suspend fun register(name: String, email: String, password: String): Result<UserResponse>

    suspend fun login(email: String, password: String): Result<UserResponse>

}