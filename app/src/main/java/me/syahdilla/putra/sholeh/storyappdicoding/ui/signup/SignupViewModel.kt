package me.syahdilla.putra.sholeh.storyappdicoding.ui.signup

import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeRunOnce
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignupViewModel(
    private val apiRepository: ApiRepository,
    application: android.app.Application
): AndroidViewModel(application) {

    private val context
        get() = getApplication<me.syahdilla.putra.sholeh.storyappdicoding.Application>().applicationContext

    private val _state = MutableStateFlow<DefaultEvent>(DefaultEvent.Nothing)
    val state = _state.asStateFlow()

    fun register(name: String, email: String, password: String) = safeRunOnce(33) {
        _state.emit(DefaultEvent.InProgress)

        val response = apiRepository.register(name, email, password).getOrNull()
        val message = response?.message ?: context.getString(R.string.register_failure)
        val isFailure = response?.error ?: true
        _state.emit(
            if (isFailure)
                DefaultEvent.Failure(message)
            else
                DefaultEvent.Success
        )
    }

}