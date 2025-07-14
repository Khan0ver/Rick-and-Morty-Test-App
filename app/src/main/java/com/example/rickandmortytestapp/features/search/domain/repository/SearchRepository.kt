package com.example.rickandmortytestapp.features.search.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortytestapp.features.search.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getAllFilteredCharacters(filterMap: Map<String, String>): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Long): Flow<Character>

    suspend fun tryUpdateInfoAboutCharacter(id: Long)
}