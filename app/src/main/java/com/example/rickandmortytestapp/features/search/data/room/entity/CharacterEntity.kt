package com.example.rickandmortytestapp.features.search.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rickandmortytestapp.features.search.data.room.converters.Converter

@Entity(tableName = "characters")
data class CharacterEntity (
    @PrimaryKey
    val id: Long,
    val episode: List<String>? = null,
    val created: String? = null,
    val gender: String? = null,
    val image: String? = null,
    val currentLocationName: String? = null,
    val name: String? = null,
    val originLocationName: String? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null,
)