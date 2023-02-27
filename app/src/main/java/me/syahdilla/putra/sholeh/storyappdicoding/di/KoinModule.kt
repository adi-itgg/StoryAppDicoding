package me.syahdilla.putra.sholeh.storyappdicoding.di

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import me.syahdilla.putra.sholeh.storyappdicoding.CustomLogger
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.database.story.StoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.database.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.storyappdicoding.network.*
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.LoadingAdapter
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.StoryListAdapterPaging
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.AddStoryViewModel
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.CameraManager
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.GalleryManager
import me.syahdilla.putra.sholeh.storyappdicoding.ui.addstory.ImageManager
import me.syahdilla.putra.sholeh.storyappdicoding.ui.dialog.LoadingDialog
import me.syahdilla.putra.sholeh.storyappdicoding.ui.login.LoginViewModel
import me.syahdilla.putra.sholeh.storyappdicoding.ui.main.MainViewModel
import me.syahdilla.putra.sholeh.storyappdicoding.ui.signup.SignupViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val diModules by lazy {
    listOf(
        singletons,
        viewmodels,
        factories
    )
}

private val singletons = module {
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
    } bind StoryDatabase::class
    singleOf(::ApiRepositoryImpl) bind ApiRepository::class
    singleOf(::ImageManager)
}

private val viewmodels = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SignupViewModel)
    viewModelOf(::AddStoryViewModel)
}

private val factories = module {
    factoryOf(::StoryListAdapterPaging)
    factoryOf(::LoadingAdapter)
    factoryOf(::GalleryManager)
    factoryOf(::CameraManager)
    factoryOf(::LoadingDialog)
    factoryOf(::StoryRemoteMediator)
    factoryOf(::StoryPagingImpl) bind StoryPaging::class
}