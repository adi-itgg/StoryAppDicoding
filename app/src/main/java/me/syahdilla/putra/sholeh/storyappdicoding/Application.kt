package me.syahdilla.putra.sholeh.storyappdicoding

import android.app.Application
import me.syahdilla.putra.sholeh.storyappdicoding.core.di.coreModule
import me.syahdilla.putra.sholeh.storyappdicoding.utils.tryRun
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module


@Module
@ComponentScan("me.syahdilla.putra.sholeh")
class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(coreModule, module)
        }

    }

}

val isUITest by lazy {
    tryRun {
        Thread.currentThread().stackTrace.any {
            it.className == "androidx.test.runner.AndroidJUnitRunner" ||
                    it.className == "androidx.test.runner.MonitoringInstrumentation"
        }
    }.getOrDefault(false)
}