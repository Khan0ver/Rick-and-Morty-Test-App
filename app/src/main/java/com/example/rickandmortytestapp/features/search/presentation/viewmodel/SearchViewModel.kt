package com.example.rickandmortytestapp.features.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortytestapp.features.search.domain.model.Character
import com.example.rickandmortytestapp.features.search.domain.usecase.GetAllFilteredCharactersUseCase
import com.example.rickandmortytestapp.features.search.domain.usecase.GetCharacterByIdUseCase
import com.example.rickandmortytestapp.features.search.domain.usecase.TryUpdateCharacterInfoByIdUseCase
import com.example.rickandmortytestapp.features.search.presentation.model.FilterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllFilteredCharactersUseCase: GetAllFilteredCharactersUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val tryUpdateCharacterInfoByIdUseCase: TryUpdateCharacterInfoByIdUseCase,
) : ViewModel() {
    private var _filter = MutableStateFlow(FilterInfo())
    val filter: StateFlow<FilterInfo> = _filter

    private var filterMap = mutableMapOf(
        "name" to _filter.value.name,
        "status" to _filter.value.status,
        "species" to _filter.value.species,
        "type" to _filter.value.type,
        "gender" to _filter.value.gender,
    )

    var characters = flow<PagingData<Character>> {}
    var character = flow<Character?> {}

    fun changeName(name: String) {
        _filter.value = _filter.value.copy(
            name = name
        )
    }

    fun changeStatus(status: String) {
        _filter.value = _filter.value.copy(
            status = status
        )
    }

    fun changeSpecies(species: String) {
        _filter.value = _filter.value.copy(
            species = species
        )
    }

    fun changeType(type: String) {
        _filter.value = _filter.value.copy(
            type = type
        )
    }

    fun changeGender(gender: String) {
        _filter.value = _filter.value.copy(
            gender = gender
        )
    }

    fun getCharactersWithFilter() {
        filterMap["name"] = _filter.value.name
        filterMap["status"] = _filter.value.status
        filterMap["species"] = _filter.value.species
        filterMap["type"] = _filter.value.type
        filterMap["gender"] = _filter.value.gender
        viewModelScope.launch {
            characters = getAllFilteredCharactersUseCase(filterMap).cachedIn(viewModelScope)
        }
    }

    fun getCharacterInfoById(id: Long) {
        viewModelScope.launch {
            character = getCharacterByIdUseCase(id)
            tryUpdateCharacterInfoByIdUseCase(id)
        }
    }
}