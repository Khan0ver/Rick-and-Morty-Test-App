package com.example.rickandmortytestapp.features.search.data.remote.service

import com.example.rickandmortytestapp.features.search.data.remote.dto.CharacterDTO
import com.example.rickandmortytestapp.features.search.data.remote.dto.CharactersResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchCharacterService {
    @GET("character/")
    suspend fun getAllCharactersWithPaging(@Query("page") page: Long): Response<CharactersResponseDTO>

    @GET("character/")
    suspend fun getFilteredCharacters(@QueryMap filterMap: Map<String, String>, @Query("page") page: Long): Response<CharactersResponseDTO>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Long): Response<CharacterDTO>
}