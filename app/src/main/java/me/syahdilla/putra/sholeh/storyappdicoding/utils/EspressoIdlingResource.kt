package me.syahdilla.putra.sholeh.storyappdicoding.utils

import androidx.test.espresso.idling.CountingIdlingResource
import me.syahdilla.putra.sholeh.storyappdicoding.BuildConfig

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow)
            countingIdlingResource.decrement()
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    EspressoIdlingResource.increment()
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement()
    }
}