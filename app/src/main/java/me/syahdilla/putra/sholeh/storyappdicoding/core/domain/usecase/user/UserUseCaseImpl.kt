package me.syahdilla.putra.sholeh.storyappdicoding.core.domain.usecase.user

import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.repository.StoryRepository
import org.koin.core.annotation.Factory


@Factory
internal class UserUseCaseImpl(
    private val repository: StoryRepository
): UserUseCase {

    override suspend fun register(name: String, email: String, password: String) = repository.register(name, email, password)

    override suspend fun login(email: String, password: String) = repository.login(email, password)

}