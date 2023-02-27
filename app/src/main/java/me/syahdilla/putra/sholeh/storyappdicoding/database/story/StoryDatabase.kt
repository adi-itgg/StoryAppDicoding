package me.syahdilla.putra.sholeh.storyappdicoding.database.story

import androidx.paging.PagingSource
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.database.remotekeys.RemoteKeysDao

interface StoryDatabase {

    val remoteKeysDao: RemoteKeysDao

    suspend fun insertStories(stories: List<Story>)

    fun getAllStories(): PagingSource<Int, Story>

    suspend fun deleteAll()

    suspend fun<R> withTransaction(block: suspend () -> R): R

}