package com.example.rickandmortytestapp.features.search.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_short_info")
data class LocationShortInfoEntity(
    @PrimaryKey
    val name: String = "unknown",
    val url: String? = null,
)