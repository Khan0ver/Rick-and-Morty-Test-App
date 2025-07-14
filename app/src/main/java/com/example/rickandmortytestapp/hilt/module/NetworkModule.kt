package com.example.rickandmortytestapp.hilt.module

import com.example.rickandmortytestapp.features.search.data.remote.LoggerInterceptor
import com.example.rickandmortytestapp.features.search.data.remote.service.SearchCharacterService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpOkClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(LoggerInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(
                Json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchCharacterService(retrofit: Retrofit): SearchCharacterService =
        retrofit.create(SearchCharacterService::class.java)
}