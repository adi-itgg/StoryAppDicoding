package me.syahdilla.putra.sholeh.storyappdicoding.ui.login

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.*
import me.syahdilla.putra.sholeh.storyappdicoding.*
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.LoginEvent
import me.syahdilla.putra.sholeh.storyappdicoding.utils.asJson
import me.syahdilla.putra.sholeh.storyappdicoding.utils.asObject
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeRunOnce

class LoginViewModel(
    private val apiRepository: ApiRepository,
    application: Application
): AndroidViewModel(application) {

    private val logger by customLogger()

    private val _state = MutableStateFlow<LoginEvent>(LoginEvent.Nothing)
    val state = _state.asStateFlow()

    private val context: Context
        get() = getApplication<App>().applicationContext

    fun login(email: String, password: String) = safeRunOnce(1) {
        _state.emit(LoginEvent.InProgress)

        val response = apiRepository.login(
            email = email,
            password = password
        ).getOrNull()

        response?.loginResult?.let { session ->
            context.dataStore.edit {
                it[PREF_USER_SESSION] = session.asJson()
            }
            logger.debug("login success and saved user session")
            _state.emit(LoginEvent.Success(session))
            return@safeRunOnce
        }
        logger.debug("login failure, cause ${if (response == null) "response is null" else "response user session is null"}")

        val msg = response?.message ?: context.getString(R.string.login_failure)
        _state.emit(LoginEvent.Failure(msg))
    }

    suspend fun hasSession() = context.dataStore.data.firstOrNull()?.get(PREF_USER_SESSION)?.let {
        logger.debug("user session available")
        _state.emit(LoginEvent.Success(it.asObject()))
        true
    } ?: false


}