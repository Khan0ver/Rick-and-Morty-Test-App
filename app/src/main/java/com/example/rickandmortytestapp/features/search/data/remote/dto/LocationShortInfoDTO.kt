package com.example.rickandmortytestapp.features.search.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationShortInfoDTO(
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String? = null,
)