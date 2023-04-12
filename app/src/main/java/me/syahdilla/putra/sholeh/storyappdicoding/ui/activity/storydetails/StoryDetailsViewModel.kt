package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.tasks.Task
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import org.koin.android.annotation.KoinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

@KoinViewModel
class StoryDetailsViewModel(
    app: Application
): AndroidViewModel(app) {

    private val context
        get() = getApplication<Application>().applicationContext

    private val moduleFavorite = "favorit"
    private val splitInstallManager by lazy {
        SplitInstallManagerFactory.create(context)
    }

    fun isInstalledFavorite(): Boolean {
        if (!splitInstallManager.installedModules.contains(moduleFavorite)) {
            return false
        }
        val favClazz: Any = Class.forName("me.syahdilla.putra.sholeh.favorit.di.FavModule").newInstance()
        val field = favClazz.javaClass.getDeclaredField("modules")
        field.isAccessible = true
        val module: Module = field.get(favClazz) as Module
        loadKoinModules(module)
        return true
    }

    fun installFavorite(): Task<Int> {
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleFavorite)
            .build()
        return splitInstallManager.startInstall(request)
    }


    suspend fun isInFavorite(obj: Any, id: String) =
        obj::class.memberFunctions.find { it.name == "getFavorite" }?.let {
            it.isAccessible = true
            it.callSuspend(obj, id) != null
        } ?: false

    suspend fun addOrDeleteFavorite(obj: Any, story: Story) {
        if (isInFavorite(obj, story.id)) {
            obj::class.memberFunctions.find { it.name == "deleteFavorite" }?.let {
                it.isAccessible = true
                it.callSuspend(obj, story.id)
            }
            return
        }

        obj::class.memberFunctions.find {
            it.name == "addFavorite"
        }?.let {
            it.isAccessible = true
            it.callSuspend(obj, story)
        }
    }

}