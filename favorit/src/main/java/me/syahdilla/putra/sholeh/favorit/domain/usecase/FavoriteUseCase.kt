package me.syahdilla.putra.sholeh.favorit.domain.usecase

import androidx.annotation.Keep
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.domain.model.Story

@Keep
interface FavoriteUseCase {

    fun getFavorites(): Flow<List<Story>>

    suspend fun addFavorite(story: Story)

    suspend fun getFavorite(id: String): Story?

    suspend fun deleteFavorite(id: String)

}