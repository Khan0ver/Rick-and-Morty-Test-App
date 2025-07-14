package com.example.rickandmortytestapp.features.search.data.room.mapper

import com.example.rickandmortytestapp.features.search.data.remote.dto.CharacterDTO
import com.example.rickandmortytestapp.features.search.data.remote.dto.LocationShortInfoDTO
import com.example.rickandmortytestapp.features.search.data.room.entity.CharacterEntity
import com.example.rickandmortytestapp.features.search.data.room.entity.LocationShortInfoEntity

fun CharacterDTO.toRoomEntity(): CharacterEntity = CharacterEntity(
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

fun LocationShortInfoDTO.toRoomEntity(): LocationShortInfoEntity = LocationShortInfoEntity(
    name = this.name ?: "unknown",
    url = this.url,
)