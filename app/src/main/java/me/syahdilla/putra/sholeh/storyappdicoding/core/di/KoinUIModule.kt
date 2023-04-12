package me.syahdilla.putra.sholeh.storyappdicoding.core.di

import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.LoadingAdapter
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.StoryListAdapterPaging
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val uiModule = module {
    factoryOf(::LoadingAdapter)
    factoryOf(::StoryListAdapterPaging)
}