package com.example.rickandmortytestapp.features.search.domain.usecase

import com.example.rickandmortytestapp.features.search.domain.model.Character
import com.example.rickandmortytestapp.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(id: Long): Flow<Character> {
        return repository.getCharacterById(id)
    }
}