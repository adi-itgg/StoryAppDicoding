package me.syahdilla.putra.sholeh.story.core.di

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManager
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManagerImpl
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.CustomLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val coreModule = module {

    single {
        val logger = CustomLogger("Http")
        val logging = HttpLoggingInterceptor {
            logger.verbose(it)
        }.apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        OkHttpClient.Builder().addInterceptor(logging).build()
    }

    single {
        object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Story, newItem: Story) = oldItem == newItem
        }
    } bind DiffUtil.ItemCallback::class

    single {
        Room.databaseBuilder(androidContext(), StoryDatabaseImpl::class.java, "story-db")
            .fallbackToDestructiveMigration()
            .build()
    } bind StoryDatabase::class

    singleOf(::RetrofitManagerImpl) bind RetrofitManager::class

}