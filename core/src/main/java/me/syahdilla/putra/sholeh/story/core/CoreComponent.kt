package me.syahdilla.putra.sholeh.story.core

import android.content.Context
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Module
@ComponentScan("me.syahdilla.putra.sholeh")
class CoreComponent: KoinComponent {
    val context: Context by inject()
}