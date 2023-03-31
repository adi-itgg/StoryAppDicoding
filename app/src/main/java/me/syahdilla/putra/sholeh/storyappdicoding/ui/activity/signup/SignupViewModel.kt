package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.syahdilla.putra.sholeh.storyappdicoding.App
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.story.core.domain.usecase.user.UserUseCase
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import me.syahdilla.putra.sholeh.story.core.utils.safeRunOnce
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignupViewModel(
    private val userUseCase: UserUseCase,
    application: Application
): AndroidViewModel(application) {

    private val context
        get() = getApplication<App>().applicationContext

    private val _state = MutableStateFlow<DefaultEvent>(DefaultEvent.Nothing)
    val state = _state.asStateFlow()

    fun register(name: String, email: String, password: String) = safeRunOnce(33) {
        _state.emit(DefaultEvent.InProgress)

        userUseCase.register(name, email, password).onSuccess {
            _state.emit(DefaultEvent.Success)
        }.onFailure {
            _state.emit(DefaultEvent.Failure(it.message ?: context.getString(R.string.register_failure)))
        }
    }

}