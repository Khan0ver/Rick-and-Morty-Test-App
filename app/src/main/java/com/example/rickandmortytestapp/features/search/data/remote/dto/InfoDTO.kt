package com.example.rickandmortytestapp.features.search.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoDTO(
    @SerialName("count")
    val count: Int? = null,
    @SerialName("next")
    val next: String? = null,
    @SerialName("pages")
    val pages: Int? = null,
    @SerialName("prev")
    val prev: String? = null,
)