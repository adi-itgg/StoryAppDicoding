package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import me.syahdilla.putra.sholeh.favorit.di.favoriteModule
import me.syahdilla.putra.sholeh.favorit.domain.usecase.FavoriteUseCase
import org.koin.android.annotation.KoinViewModel
import org.koin.core.context.loadKoinModules

@KoinViewModel
class StoryDetailsViewModel(
    private val context: Context
) {

    fun isInstalledFavorite(): Boolean {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        val module = "favorite"
        if (!splitInstallManager.installedModules.contains(module)) {
            return false
        }
        loadKoinModules(favoriteModule)
        return true;
    }

}