package me.syahdilla.putra.sholeh.story.core.data.source.local.room.story

import androidx.paging.PagingSource
import androidx.room.RoomDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.remotekeys.RemoteKeysDao

abstract class StoryDatabase: RoomDatabase() {

    abstract val remoteKeysDao: RemoteKeysDao

    abstract suspend fun insertStories(stories: List<StoryEntity>)

    abstract fun getAllStories(): PagingSource<Int, StoryEntity>

    abstract suspend fun deleteAll()

    abstract suspend fun<R> withTransaction(block: suspend () -> R): R

}