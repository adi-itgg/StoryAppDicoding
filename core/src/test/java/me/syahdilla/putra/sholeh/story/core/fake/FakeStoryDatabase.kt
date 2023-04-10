@file:Suppress("CAST_NEVER_SUCCEEDS")

package me.syahdilla.putra.sholeh.story.core.fake

import androidx.paging.PagingSource
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.remotekeys.RemoteKeysDao
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase

class FakeStoryDatabase: StoryDatabase {
    override val remoteKeysDao: RemoteKeysDao
        get() = null as RemoteKeysDao

    override suspend fun insertStories(stories: List<StoryEntity>) {}
    override fun getAllStories(): PagingSource<Int, StoryEntity> = null as PagingSource<Int, StoryEntity>

    override suspend fun deleteAll() {
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R {
        return block()
    }
}