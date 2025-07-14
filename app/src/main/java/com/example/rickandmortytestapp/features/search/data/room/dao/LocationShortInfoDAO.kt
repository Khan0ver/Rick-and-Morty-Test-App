package com.example.rickandmortytestapp.features.search.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortytestapp.features.search.data.room.entity.LocationShortInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationShortInfoDAO {
    @Insert(
        entity = LocationShortInfoEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(location: LocationShortInfoEntity)

    @Query("SELECT * FROM location_short_info WHERE name = :name")
    suspend fun getLocationShortInfo(name: String): LocationShortInfoEntity

    @Query("DELETE FROM location_short_info")
    suspend fun clearAll()
}