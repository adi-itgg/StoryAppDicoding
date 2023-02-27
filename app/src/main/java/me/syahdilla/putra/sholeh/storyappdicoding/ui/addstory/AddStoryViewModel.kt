package me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.syahdilla.putra.sholeh.storyappdicoding.App
import me.syahdilla.putra.sholeh.storyappdicoding.UserManager
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeRunOnce
import me.syahdilla.putra.sholeh.storyappdicoding.ui.event.DefaultEvent
import java.io.File

class AddStoryViewModel(
    private val apiRepository: ApiRepository,
    private val imageManager: ImageManager,
    application: Application
): AndroidViewModel(application) {

    val context: Context
        get() = getApplication<App>().applicationContext

    lateinit var photoUri: Uri
    val isPhotoAvailable
        get() = this::photoUri.isInitialized

    lateinit var photoFile: File
    val isPhotoFileAvailable
        get() = this::photoFile.isInitialized

    private val _state = MutableSharedFlow<DefaultEvent>()
    val state = _state.asSharedFlow()

    fun upload(
        description: String,
        lat: Float?,
        lon: Float?
    ) = safeRunOnce(46) {
        _state.emit(DefaultEvent.InProgress)

        val res = (if (isPhotoFileAvailable)
            apiRepository.createStory(
                token = UserManager.session.token,
                description = description,
                photo = photoFile,
                lat = lat,
                lon = lon
            )
        else
            apiRepository.createStory(
                imageManager = imageManager,
                token = UserManager.session.token,
                description = description,
                photo = photoUri,
                lat = lat,
                lon = lon
            )) ?: return@safeRunOnce _state.emit(DefaultEvent.Failure("Failure write image file to temporary folder!"))


        res.onSuccess {
            _state.emit(
                if (it.error)
                    DefaultEvent.Failure(it.message)
                else
                    DefaultEvent.Success
            )
        }.onFailure {
            _state.emit(DefaultEvent.Failure("Connection to api error!"))
        }
    }

}