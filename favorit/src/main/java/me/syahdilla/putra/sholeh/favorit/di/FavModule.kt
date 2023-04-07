package me.syahdilla.putra.sholeh.favorit.di

import androidx.room.Room
import me.syahdilla.putra.sholeh.favorit.activity.FavoriteViewModel
import me.syahdilla.putra.sholeh.favorit.data.FavoriteRepositoryImpl
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabase
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabaseImpl
import me.syahdilla.putra.sholeh.favorit.domain.repository.FavoriteRepository
import me.syahdilla.putra.sholeh.favorit.domain.usecase.FavoriteUseCase
import me.syahdilla.putra.sholeh.favorit.domain.usecase.FavoriteUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

class FavModule {
    val module = module {
        single {
            Room.databaseBuilder(androidContext(), FavoriteDatabaseImpl::class.java, "favorite-db")
                .openHelperFactory(get())
                .build()
        } bind FavoriteDatabase::class
        singleOf(::FavoriteRepositoryImpl) bind FavoriteRepository::class
        single(named("favoriteUseCase")) { FavoriteUseCaseImpl(get()) } binds arrayOf(FavoriteUseCase::class, Any::class)
        viewModel { FavoriteViewModel(get(qualifier = named("favoriteUseCase"))) }
    }
}