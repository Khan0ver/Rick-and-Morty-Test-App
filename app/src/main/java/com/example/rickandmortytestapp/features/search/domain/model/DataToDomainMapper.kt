package com.example.rickandmortytestapp.features.search.domain.model

import com.example.rickandmortytestapp.features.search.data.remote.dto.CharacterDTO
import com.example.rickandmortytestapp.features.search.data.room.entity.CharacterEntity

fun CharacterEntity.toDomain(): Character = Character(
    id = this.id,
    episode = this.episode,
    created = this.created,
    gender = this.gender,
    image = this.image,
    currentLocationName = this.currentLocationName,
    name = this.name,
    originLocationName = this.originLocationName,
    species = this.species,
    status = this.status,
    type = this.type,
    url = this.url,
)

fun CharacterDTO.toDomain(): Character = Character(
    id = this.id,
    episode = this.episode,
    created = this.created,
    gender = this.gender,
    image = this.image,
    currentLocationName = this.locationShortInfoDTO?.name,
    name = this.name,
    originLocationName = this.originDTO?.name,
    species = this.species,
    status = this.status,
    type = this.type,
    url = this.url,
)