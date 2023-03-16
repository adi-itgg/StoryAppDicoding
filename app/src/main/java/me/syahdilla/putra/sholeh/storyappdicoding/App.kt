package me.syahdilla.putra.sholeh.storyappdicoding

import com.google.android.play.core.splitcompat.SplitCompatApplication
import me.syahdilla.putra.sholeh.story.core.CoreComponent
import me.syahdilla.putra.sholeh.story.core.di.coreModule
import me.syahdilla.putra.sholeh.storyappdicoding.core.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module


@Module
@ComponentScan("me.syahdilla.putra.sholeh")
class App: SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(coreModule, CoreComponent().module, uiModule, module)
        }

    }

}