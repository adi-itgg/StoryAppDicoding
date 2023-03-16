package me.syahdilla.putra.sholeh.favorit.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

@Database(
    entities = [StoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDatabaseImpl: FavoriteDatabase, RoomDatabase() {

    abstract fun dao(): FavoriteDAO

    override fun getFavorites() = dao().getAllFavorite()

    override suspend fun addFavorite(storyEntity: StoryEntity) = withContext(IO) { dao().addFavorite(storyEntity) }

    override suspend fun getFavorite(id: String): StoryEntity? = withContext(IO) { dao().getFavorite(id) }

    override suspend fun deleteFavorite(id: String) = withContext(IO) { dao().deleteFavorite(id) }

}