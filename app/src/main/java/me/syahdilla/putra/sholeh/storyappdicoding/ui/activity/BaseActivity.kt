package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Dispatchers.Main
import me.syahdilla.putra.sholeh.story.core.utils.customLogger
import me.syahdilla.putra.sholeh.story.core.utils.safeLaunch
import me.syahdilla.putra.sholeh.story.core.utils.viewBinding
import kotlin.reflect.KClass

abstract class BaseActivity<out T: ViewBinding>(
    bindingInflater: (LayoutInflater) -> T
): AppCompatActivity() {

    val logger by customLogger()

    val binding: T by viewBinding(bindingInflater)

    val mThis
        get() = this

    open val showTopLeftBackButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (showTopLeftBackButton)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        safeLaunch(Main.immediate) {
            onInitialize(savedInstanceState)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!showTopLeftBackButton) return super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    /**
     * onInitialize called from onCreate after [setContentView] and run in [Main] immediate dispatcher
     */
    abstract suspend fun onInitialize(savedInstanceState: Bundle?)


    /**
     * Open activity
     */
    inline fun <reified T: KClass<*>> T.openActivity(useActivityTransition: Boolean = true, vararg sharedElements: Pair<View, String>, builder: Intent.() -> Unit = {}) {
        val options = if (sharedElements.isNotEmpty())
            ActivityOptionsCompat.makeSceneTransitionAnimation(mThis, *sharedElements.map { androidx.core.util.Pair(it.first, it.second) }.toTypedArray()).toBundle()
        else if (useActivityTransition)
            ActivityOptionsCompat.makeSceneTransitionAnimation(mThis).toBundle()
        else
            null
        startActivity(Intent(mThis, java).apply(builder), options)
    }

    /**
     * Open activity
     */
    inline fun <reified T: KClass<*>> ActivityResultLauncher<Intent>.open(clazz: T, useActivityTransition: Boolean = true, vararg sharedElements: Pair<View, String>, builder: Intent.() -> Unit = {}) {
        val options = if (sharedElements.isNotEmpty())
            ActivityOptionsCompat.makeSceneTransitionAnimation(mThis, *sharedElements.map { androidx.core.util.Pair(it.first, it.second) }.toTypedArray())
        else if (useActivityTransition)
            ActivityOptionsCompat.makeSceneTransitionAnimation(mThis)
        else null
        launch(Intent(mThis, clazz.java).apply(builder), options)
    }

}