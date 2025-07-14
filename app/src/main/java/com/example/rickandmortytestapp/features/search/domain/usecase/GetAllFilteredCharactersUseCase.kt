package com.example.rickandmortytestapp.features.search.domain.usecase

import androidx.paging.PagingData
import com.example.rickandmortytestapp.features.search.domain.model.Character
import com.example.rickandmortytestapp.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFilteredCharactersUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(filterMap: Map<String, String>): Flow<PagingData<Character>> {
        return repository.getAllFilteredCharacters(filterMap)
    }
}