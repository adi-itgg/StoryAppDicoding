package me.syahdilla.putra.sholeh.storyappdicoding.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.syahdilla.putra.sholeh.storyappdicoding.App
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeRunOnce

class SignupViewModel(
    private val apiRepository: ApiRepository,
    application: Application
): AndroidViewModel(application) {

    private val context
        get() = getApplication<App>().applicationContext

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