package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.DataMapper
import org.koin.android.annotation.KoinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

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
        val m = favClazz.javaClass.getDeclaredMethod("module")
        val module: Module = m.invoke(favClazz) as Module
        loadKoinModules(module)
        return true
    }

    fun installFavorite(): Task<Int> {
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleFavorite)
            .build()
        return splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                Toast.makeText(context, "Success installing module", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Error installing module", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, it.stackTraceToString(), Toast.LENGTH_SHORT).show()
            }
    }


    suspend fun isInFavorite(obj: Any, id: String): Boolean = suspendCancellableCoroutine { cont ->
        obj.javaClass.getDeclaredMethod(
            "getFavorite"
        ).invoke(obj, id, cont) != null
    }

    suspend fun addOrDeleteFavorite(obj: Any, story: Story) {
        if (isInFavorite(obj, story.id)) {
            suspendCancellableCoroutine<Unit> { cont ->
                obj.javaClass.getDeclaredMethod(
                    "deleteFavorite"
                ).invoke(obj, story.id, cont)
            }
            return
        }

        suspendCancellableCoroutine<Unit> { cont ->
            obj.javaClass.getDeclaredMethod(
                "addFavorite"
            ).invoke(obj, DataMapper.mapDomainToEntity(story), cont)
        }
    }

}