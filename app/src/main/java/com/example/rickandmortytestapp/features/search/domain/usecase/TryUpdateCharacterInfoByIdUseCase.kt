package com.example.rickandmortytestapp.features.search.domain.usecase

import com.example.rickandmortytestapp.features.search.domain.repository.SearchRepository
import javax.inject.Inject

class TryUpdateCharacterInfoByIdUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.tryUpdateInfoAboutCharacter(id)
    }
}