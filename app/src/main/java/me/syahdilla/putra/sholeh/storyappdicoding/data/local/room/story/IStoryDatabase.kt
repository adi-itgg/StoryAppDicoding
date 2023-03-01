package me.syahdilla.putra.sholeh.storyappdicoding.data.local.room.story

import androidx.paging.PagingSource
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.data.local.room.remotekeys.RemoteKeysDao

interface IStoryDatabase {

    val remoteKeysDao: RemoteKeysDao

    suspend fun insertStories(stories: List<Story>)

    fun getAllStories(): PagingSource<Int, Story>

    suspend fun deleteAll()

    suspend fun<R> withTransaction(block: suspend () -> R): R

}