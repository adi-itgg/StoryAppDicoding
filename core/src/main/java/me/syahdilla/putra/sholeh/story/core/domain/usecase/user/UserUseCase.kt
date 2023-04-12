package me.syahdilla.putra.sholeh.story.core.domain.usecase.user

import me.syahdilla.putra.sholeh.story.core.domain.model.User

interface UserUseCase {

    suspend fun register(name: String, email: String, password: String): Result<Boolean>

    suspend fun login(email: String, password: String): Result<User>

}