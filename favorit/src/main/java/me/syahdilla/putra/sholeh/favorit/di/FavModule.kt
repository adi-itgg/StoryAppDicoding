package me.syahdilla.putra.sholeh.favorit.di

import androidx.room.Room
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabase
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabaseImpl
import me.syahdilla.putra.sholeh.favorit.domain.usecase.FavoriteUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

class FavModule {
    val module = module {
        single {
            Room.databaseBuilder(androidContext(), FavoriteDatabaseImpl::class.java, "favorite-db")
                .build()
        } bind FavoriteDatabase::class
        single(named("favoriteUseCase")) { FavoriteUseCaseImpl(get()) }
    }
}