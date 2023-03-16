package me.syahdilla.putra.sholeh.story.core.data.source.local.room.story

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.RemoteKeysEntity
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.remotekeys.RemoteKeysDao


@Database(
    entities = [StoryEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabaseImpl: StoryDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    override val remoteKeysDao: RemoteKeysDao
        get() = remoteKeysDao()

    override suspend fun insertStories(stories: List<StoryEntity>) = storyDao().insertStories(stories)

    override fun getAllStories() = storyDao().getAllStories()

    override suspend fun deleteAll() {
        storyDao().deleteAll()
        remoteKeysDao().deleteRemoteKeys()
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R {
        return (this as RoomDatabase).withTransaction(block)
    }

}