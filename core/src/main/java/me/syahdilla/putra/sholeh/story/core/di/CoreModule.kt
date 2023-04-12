package me.syahdilla.putra.sholeh.story.core.di

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import me.syahdilla.putra.sholeh.story.core.BuildConfig
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManager
import me.syahdilla.putra.sholeh.story.core.data.source.remote.RetrofitManagerImpl
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.CustomLogger
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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

        val hostname = Uri.parse(BuildConfig.BASE_API_URL).host as String
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/e7XtzYPBKvhKK4LUmQT2w2lpFxjVZX2u5cZ4PiZ5X6g=")
            .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Story, newItem: Story) = oldItem == newItem
        }
    } bind DiffUtil.ItemCallback::class

    single {
        SupportFactory(BuildConfig.SECRET_PASS.toByteArray())
    }

    single {
        Room.databaseBuilder(androidContext(), StoryDatabaseImpl::class.java, "story-db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(get<SupportFactory>())
            .build()
    } bind StoryDatabase::class

    singleOf(::RetrofitManagerImpl) bind RetrofitManager::class

}