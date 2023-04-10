package me.syahdilla.putra.sholeh.story.core.data.source.local.room.story

import androidx.paging.PagingSource
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.remotekeys.RemoteKeysDao

interface StoryDatabase {

    val remoteKeysDao: RemoteKeysDao

    suspend fun insertStories(stories: List<StoryEntity>)

    fun getAllStories(): PagingSource<Int, StoryEntity>

    suspend fun deleteAll()

    suspend fun<R> withTransaction(block: suspend () -> R): R

}