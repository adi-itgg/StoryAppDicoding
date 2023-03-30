package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.maps

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import me.syahdilla.putra.sholeh.story.core.UserManager
import me.syahdilla.putra.sholeh.story.core.domain.usecase.story.StoryUseCase
import me.syahdilla.putra.sholeh.story.core.utils.image.ImageManager
import me.syahdilla.putra.sholeh.storyappdicoding.R
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MapViewModel(
    private val storyUseCase: StoryUseCase,
    private val imageManager: ImageManager,
    app: Application
): AndroidViewModel(app) {

    private val context: Context
        get() = getApplication<Application>().applicationContext

    suspend fun getMarkIcon() = imageManager.vectorToBitmap(
        R.drawable.ic_round_account_circle_24,
        ContextCompat.getColor(context, R.color.story_marker_color)
    )

    suspend fun getStories(page: Int, size: Int = 10) = storyUseCase.getStories(
        user = UserManager.session,
        withLocation = true,
        page = page,
        size = size
    )

}