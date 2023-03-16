package me.syahdilla.putra.sholeh.story.core.data.source.local.room.story

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

@Dao
interface StoryDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryEntity>

    @Query("DELETE FROM story")
    suspend fun deleteAll()

}