package me.syahdilla.putra.sholeh.storyappdicoding.core.di

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import me.syahdilla.putra.sholeh.storyappdicoding.CustomLogger
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.data.local.room.story.IStoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.data.local.room.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.storyappdicoding.network.*
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.LoadingAdapter
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.StoryListAdapterPaging
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.CameraManager
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.GalleryManager
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.ImageManager
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
    single { RetrofitManager(client = get()) }
    single {
        object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Story, newItem: Story) = oldItem == newItem
        }
    } bind DiffUtil.ItemCallback::class
    single {
        Room.databaseBuilder(androidContext(), StoryDatabaseImpl::class.java, "story_database")
            .fallbackToDestructiveMigration()
            .build()
    } bind IStoryDatabase::class
}