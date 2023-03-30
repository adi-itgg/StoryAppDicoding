package me.syahdilla.putra.sholeh.favorit.activity

import androidx.lifecycle.ViewModel
import me.syahdilla.putra.sholeh.favorit.domain.usecase.FavoriteUseCase

class FavoriteViewModel(
    private val favoriteUseCase: FavoriteUseCase
): ViewModel() {

    suspend fun deleteFavorite(id: String) = favoriteUseCase.deleteFavorite(id)

    fun getFavorites() = favoriteUseCase.getFavorites()

}