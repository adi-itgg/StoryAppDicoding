package me.syahdilla.putra.sholeh.favorit.domain.repository

import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

interface FavoriteRepository {

    fun getFavorites(): Flow<List<StoryEntity>>

    suspend fun addFavorite(storyEntity: StoryEntity)

    suspend fun getFavorite(id: String): StoryEntity?

    suspend fun deleteFavorite(id: String)

}