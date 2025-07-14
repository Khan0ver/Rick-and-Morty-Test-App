package com.example.rickandmortytestapp.features.search.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDTO(
    @SerialName("info")
    val infoDTO: InfoDTO,
    @SerialName("results")
    val characters: List<CharacterDTO>,
)