package me.syahdilla.putra.sholeh.storyappdicoding.utils

import me.syahdilla.putra.sholeh.storyappdicoding.di.testModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface KoinTesting: KoinTest {

    fun extraModules(): Module {
        return module {  }
    }

    @BeforeTest
    fun setup() {
        if (KoinPlatformTools.defaultContext().getOrNull() != null) return
        startKoin {
            modules(testModules)
            modules(extraModules())
        }
    }

    @AfterTest
    fun dispose() {
        unloadKoinModules(testModules)
        stopKoin()
    }

}