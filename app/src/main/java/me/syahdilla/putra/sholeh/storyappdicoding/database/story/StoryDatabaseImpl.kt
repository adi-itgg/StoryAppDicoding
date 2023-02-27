package me.syahdilla.putra.sholeh.storyappdicoding.database.story

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import me.syahdilla.putra.sholeh.storyappdicoding.data.RemoteKeys
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.database.remotekeys.RemoteKeysDao

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabaseImpl: StoryDatabase, RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    override val remoteKeysDao: RemoteKeysDao
        get() = remoteKeysDao()

    override suspend fun insertStories(stories: List<Story>) = storyDao().insertStories(stories)

    override fun getAllStories() = storyDao().getAllStories()

    override suspend fun deleteAll() {
        storyDao().deleteAll()
        remoteKeysDao().deleteRemoteKeys()
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R {
        return (this as RoomDatabase).withTransaction(block)
    }

}