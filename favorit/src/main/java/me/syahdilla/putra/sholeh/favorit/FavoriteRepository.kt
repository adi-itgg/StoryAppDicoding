package me.syahdilla.putra.sholeh.favorit

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

class FavoriteRepository(
    private val database: FavoriteDatabase
) {

    fun getFavorites() = database.getFavorites()

    suspend fun addFavorite(storyEntity: StoryEntity) = withContext(IO) { database.addFavorite(storyEntity) }

    suspend fun getFavorite(id: String) = withContext(IO) { database.getFavorite(id) }

    suspend fun deleteFavorite(id: String) = withContext(IO) { database.deleteFavorite(id) }

}