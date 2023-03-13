package me.syahdilla.putra.sholeh.storyappdicoding.ui.event

sealed class LoginEvent {
    object Nothing: LoginEvent()
    object InProgress: LoginEvent()
    class Success(val session: me.syahdilla.putra.sholeh.storyappdicoding.core.data.User): LoginEvent()
    class Failure(val message: String): LoginEvent()
}

sealed class DefaultEvent {
    object Nothing: DefaultEvent()
    object InProgress: DefaultEvent()
    object Success: DefaultEvent()
    class Failure(val message: String): DefaultEvent()
}