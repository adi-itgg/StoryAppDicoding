package me.syahdilla.putra.sholeh.favorit.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity


@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(storyEntity: StoryEntity)

    @Query("SELECT * FROM story WHERE id = :id")
    suspend fun getFavorite(id: String): StoryEntity?

    @Query("DELETE FROM story WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT * FROM story")
    fun getAllFavorite(): Flow<List<StoryEntity>>

}