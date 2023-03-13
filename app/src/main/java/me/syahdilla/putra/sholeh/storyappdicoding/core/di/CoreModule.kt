package me.syahdilla.putra.sholeh.storyappdicoding.core.di

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import me.syahdilla.putra.sholeh.storyappdicoding.utils.CustomLogger
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.RetrofitManager
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.RetrofitManagerImpl
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.LoadingAdapter
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.StoryListAdapterPaging
import me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog.LoadingDialog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
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

    factoryOf(::LoadingDialog)
    factoryOf(::LoadingAdapter)
    factoryOf(::StoryListAdapterPaging)
}