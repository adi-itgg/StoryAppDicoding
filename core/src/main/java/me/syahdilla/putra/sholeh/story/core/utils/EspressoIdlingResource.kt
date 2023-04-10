package me.syahdilla.putra.sholeh.story.core.utils

import androidx.test.espresso.idling.CountingIdlingResource

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
    if (isUITest) EspressoIdlingResource.increment()
    return try {
        function()
    } finally {
        if (isUITest) EspressoIdlingResource.decrement()
    }
}