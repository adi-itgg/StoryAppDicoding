package me.syahdilla.putra.sholeh.storyappdicoding.utils

import kotlinx.coroutines.delay
import me.syahdilla.putra.sholeh.story.core.utils.tryRun

suspend fun waitUntil(action: () -> Unit) {
    val start = System.currentTimeMillis()
    while (true) {
        val r = tryRun(true) {
            action()
            true
        }.getOrDefault(false)
        if (r) break
        val took = System.currentTimeMillis() - start
        if (took >= 150_000) break // timeout 2.5 mins
        delay(5_00)
    }
}
