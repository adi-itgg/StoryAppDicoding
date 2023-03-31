package me.syahdilla.putra.sholeh.storyappdicoding.ui.event

import me.syahdilla.putra.sholeh.story.core.domain.model.User

sealed class LoginEvent {
    object Nothing: LoginEvent()
    object InProgress: LoginEvent()
    class Success(val session: User): LoginEvent()
    class Failure(val message: String): LoginEvent()
}

sealed class DefaultEvent {
    object Nothing: DefaultEvent()
    object InProgress: DefaultEvent()
    object Success: DefaultEvent()
    class Failure(val message: String): DefaultEvent()
}