package com.example.rickandmortytestapp.features.search.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    @SerialName("created")
    val created: String? = null,
    @SerialName("episode")
    val episode: List<String>? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("id")
    val id: Long,
    @SerialName("image")
    val image: String? = null,
    @SerialName("location")
    val locationShortInfoDTO: LocationShortInfoDTO? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("origin")
    val originDTO: LocationShortInfoDTO? = null,
    @SerialName("species")
    val species: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("url")
    val url: String? = null,
)