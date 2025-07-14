package com.example.rickandmortytestapp.features.search.domain.model

data class Character(
    val id: Long,
    val episode: List<String>? = null,
    val created: String? = null,
    val gender: String? = null,
    val image: String? = null,
    val currentLocationName: String? = null,
    val name: String? = null,
    val originLocationName: String? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null,
)
