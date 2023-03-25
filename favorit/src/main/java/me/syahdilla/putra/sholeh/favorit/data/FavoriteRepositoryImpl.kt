package me.syahdilla.putra.sholeh.favorit.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.favorit.data.source.local.room.FavoriteDatabase
import me.syahdilla.putra.sholeh.favorit.domain.repository.FavoriteRepository
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

class FavoriteRepositoryImpl(
    private val database: FavoriteDatabase
): FavoriteRepository {

    override fun getFavorites() = database.getFavorites()

    override suspend fun addFavorite(storyEntity: StoryEntity) = withContext(IO) { database.addFavorite(storyEntity) }

    override suspend fun getFavorite(id: String) = withContext(IO) { database.getFavorite(id) }

    override suspend fun deleteFavorite(id: String) = withContext(IO) { database.deleteFavorite(id) }

}