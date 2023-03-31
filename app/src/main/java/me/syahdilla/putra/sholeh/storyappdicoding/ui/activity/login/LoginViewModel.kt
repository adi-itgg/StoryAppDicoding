package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.login

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import me.syahdilla.putra.sholeh.storyappdicoding.*
import me.syahdilla.putra.sholeh.story.core.domain.usecase.user.UserUseCase
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.LoginEvent
import me.syahdilla.putra.sholeh.story.core.utils.asJson
import me.syahdilla.putra.sholeh.story.core.utils.asObject
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import me.syahdilla.putra.sholeh.story.core.utils.safeRunOnce
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val userUseCase: UserUseCase,
    application: Application
): AndroidViewModel(application) {

    private val logger by customLogger()

    private val _state = MutableStateFlow<LoginEvent>(LoginEvent.Nothing)
    val state = _state.asStateFlow()

    private val context: Context
        get() = getApplication<App>().applicationContext

    fun login(email: String, password: String) = safeRunOnce(1) {
        _state.emit(LoginEvent.InProgress)

         userUseCase.login(
            email = email,
            password = password
        ).onSuccess { user ->
             context.dataStore.edit {
                 it[PREF_USER_SESSION] = user.asJson()
             }
             logger.debug("login success and saved user session")
             _state.emit(LoginEvent.Success(user))
         }.onFailure {
             _state.emit(LoginEvent.Failure(it.message ?: context.getString(R.string.login_failure)))
         }
    }

    suspend fun hasSession() = context.dataStore.data.firstOrNull()?.get(PREF_USER_SESSION)?.let {
        logger.debug("user session available")
        _state.emit(LoginEvent.Success(it.asObject()))
        true
    } ?: false


}