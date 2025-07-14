package com.example.rickandmortytestapp.features.search.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmortytestapp.features.search.data.room.converters.Converter
import com.example.rickandmortytestapp.features.search.data.room.dao.CharacterDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.LocationShortInfoDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.RemoteKeyDao
import com.example.rickandmortytestapp.features.search.data.room.entity.CharacterEntity
import com.example.rickandmortytestapp.features.search.data.room.entity.LocationShortInfoEntity
import com.example.rickandmortytestapp.features.search.data.room.entity.RemoteKeyEntity

@Database(
    entities = [
        CharacterEntity::class, LocationShortInfoEntity::class, RemoteKeyEntity::class
    ], version = 1
)
@TypeConverters(Converter::class)
abstract class RickAndMortyAppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDAO
    abstract fun locationShortInfoDao(): LocationShortInfoDAO
    abstract fun remoteKeyDao(): RemoteKeyDao
}