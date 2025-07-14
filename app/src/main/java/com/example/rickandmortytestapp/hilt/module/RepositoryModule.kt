package com.example.rickandmortytestapp.hilt.module

import com.example.rickandmortytestapp.features.search.data.repository_impl.SearchRepositoryImpl
import com.example.rickandmortytestapp.features.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsSearchRepositoryToImpl(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}