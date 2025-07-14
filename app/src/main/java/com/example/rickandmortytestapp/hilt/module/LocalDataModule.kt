package com.example.rickandmortytestapp.hilt.module

import android.content.Context
import androidx.room.Room
import com.example.rickandmortytestapp.features.search.data.room.RickAndMortyAppDatabase
import com.example.rickandmortytestapp.features.search.data.room.dao.CharacterDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.LocationShortInfoDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): RickAndMortyAppDatabase =
        Room.databaseBuilder(
            applicationContext,
            RickAndMortyAppDatabase::class.java,
            "rick_and_morty_db"
        ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(database: RickAndMortyAppDatabase): CharacterDAO =
        database.characterDao()

    @Provides
    @Singleton
    fun provideLocationShortInfoDao(database: RickAndMortyAppDatabase): LocationShortInfoDAO =
        database.locationShortInfoDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: RickAndMortyAppDatabase): RemoteKeyDao =
        database.remoteKeyDao()
}