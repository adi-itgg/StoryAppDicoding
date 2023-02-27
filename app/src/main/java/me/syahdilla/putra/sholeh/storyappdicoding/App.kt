package me.syahdilla.putra.sholeh.storyappdicoding

import android.app.Application
import me.syahdilla.putra.sholeh.storyappdicoding.di.diModules
import me.syahdilla.putra.sholeh.storyappdicoding.utils.tryRun
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(diModules)
        }

    }

}

val isUITest by lazy {
    tryRun {
        val stacks = Thread.currentThread().stackTrace
        stacks.any {
            it.className == "androidx.test.runner.AndroidJUnitRunner" ||
                    it.className == "androidx.test.runner.MonitoringInstrumentation"
        }
    }.getOrDefault(false)
}