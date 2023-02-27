package me.syahdilla.putra.sholeh.storyappdicoding.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun Context.animationsEnabled(): Boolean =
    !(Settings.Global.getFloat(contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f) == 0f
            && Settings.Global.getFloat(contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1.0f) == 0f
            && Settings.Global.getFloat(contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1.0f) == 0f)

fun View.animateInfinite(lifecycle: Lifecycle, builder: ObjectAnimator.() -> Unit = {}) = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, -50f, 50f).apply {
    duration = 6000
    repeatCount = ObjectAnimator.INFINITE
    repeatMode = ObjectAnimator.REVERSE

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            if (isPaused) resume()
        }

        override fun onPause(owner: LifecycleOwner) {
            if (isRunning) pause()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            pause()
            lifecycle.removeObserver(this)
        }
    })
}.apply(builder).start()

fun ViewGroup.animateChildViews(alpha: Float = 0f, childView: (View) -> Animator): List<Animator> {
    if (alpha < 1f)
        getAllChildren {
            if (it.visibility != View.VISIBLE) return@getAllChildren
            it.alpha = alpha
        }
    val anims = mutableListOf<Animator>()
    getAllChildren {
        if (it.visibility != View.VISIBLE) return@getAllChildren
        anims.add(childView(it))
    }
    return anims
}

fun animateTogether(vararg anim: Animator) =
    AnimatorSet().apply { playTogether(*anim) }

fun List<Animator>.playSequentially() =
    AnimatorSet().also {
        it.playSequentially(this)
    }.start()

fun View.animateSlide(builder: ObjectAnimator.() -> Unit = {}) =
    ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -200.0f, 0.0f)
    ).setDuration(200).apply(builder)

fun View.animateFade(builder: ObjectAnimator.() -> Unit = {}) =
    ObjectAnimator.ofFloat(this, View.ALPHA, 1f).setDuration(200).apply(builder)

fun View.animateBounce(builder: ObjectAnimator.() -> Unit = {}) =
    ObjectAnimator.ofFloat(this, "translationX", 100f, 0f).setDuration(200).apply(builder)