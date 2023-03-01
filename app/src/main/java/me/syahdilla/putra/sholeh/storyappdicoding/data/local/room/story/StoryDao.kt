package me.syahdilla.putra.sholeh.storyappdicoding.data.local.room.story

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story

@Dao
interface StoryDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertStories(stories: List<Story>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, Story>

    @Query("DELETE FROM story")
    suspend fun deleteAll()

}