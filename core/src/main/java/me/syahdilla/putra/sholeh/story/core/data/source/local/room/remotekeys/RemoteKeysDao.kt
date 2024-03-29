package me.syahdilla.putra.sholeh.story.core.data.source.local.room.remotekeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()

}