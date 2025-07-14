package com.example.rickandmortytestapp.features.search.data.room.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converter {
    @TypeConverter
    fun fromList(list: List<String>?): String? =
        list?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toList(data: String?): List<String>? =
        data?.let {
            Json.decodeFromString<List<String>>(it)
        }
}