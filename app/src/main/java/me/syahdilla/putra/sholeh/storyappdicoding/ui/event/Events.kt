package me.syahdilla.putra.sholeh.storyappdicoding.ui.event

import me.syahdilla.putra.sholeh.storyappdicoding.data.User
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story

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

sealed class MainEvent {
    object StoryList {
        class Failure(val message: String): MainEvent()
        class Success(val storyList: List<Story>): MainEvent()
    }
}