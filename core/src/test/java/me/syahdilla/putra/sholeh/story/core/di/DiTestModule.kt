package me.syahdilla.putra.sholeh.story.core.di

import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.fake.FakeStoryDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val testModules by lazy {
    listOf(
        coreModule,
        singletons
    )
}

private val singletons = module {
    single { "https://story-api.dicoding.dev/v1/" }
    single {
        val logger = me.syahdilla.putra.sholeh.story.core.utils.CustomLogger("Http")
        val logging = HttpLoggingInterceptor {
            logger.verbose(it)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        OkHttpClient.Builder().addInterceptor(logging).build()
    }
    singleOf(::FakeStoryDatabase) bind StoryDatabase::class
}