package me.syahdilla.putra.sholeh.story.core.domain.usecase.user

import me.syahdilla.putra.sholeh.story.core.domain.model.User
import me.syahdilla.putra.sholeh.story.core.domain.repository.StoryRepository
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import org.koin.core.annotation.Factory


@Factory
internal class UserUseCaseImpl(
    private val repository: StoryRepository
): UserUseCase {

    override suspend fun register(name: String, email: String, password: String) = tryRun {
        repository.register(name, email, password).getOrThrow().loginResult as User
    }

    override suspend fun login(email: String, password: String) = tryRun {
        repository.login(email, password).getOrThrow().loginResult as User
    }

}