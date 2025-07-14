package com.example.rickandmortytestapp.features.search.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortytestapp.features.search.data.room.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(
        entity = RemoteKeyEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE label = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE label = :query")
    suspend fun deleteByQuery(query: String)
}