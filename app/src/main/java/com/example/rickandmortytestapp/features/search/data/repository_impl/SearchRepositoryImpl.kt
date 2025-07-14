package com.example.rickandmortytestapp.features.search.data.repository_impl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmortytestapp.features.search.data.remote.paging.SearchRemoteMediator
import com.example.rickandmortytestapp.features.search.data.remote.service.SearchCharacterService
import com.example.rickandmortytestapp.features.search.data.room.RickAndMortyAppDatabase
import com.example.rickandmortytestapp.features.search.data.room.dao.CharacterDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.LocationShortInfoDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.RemoteKeyDao
import com.example.rickandmortytestapp.features.search.data.room.mapper.toRoomEntity
import com.example.rickandmortytestapp.features.search.domain.model.Character
import com.example.rickandmortytestapp.features.search.domain.model.toDomain
import com.example.rickandmortytestapp.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchRepositoryImpl @Inject constructor(
    private val characterDAO: CharacterDAO,
    private val locationShortInfoDAO: LocationShortInfoDAO,
    private val remoteKeyDao: RemoteKeyDao,
    private val database: RickAndMortyAppDatabase,
    private val service: SearchCharacterService,
) : SearchRepository {
    override suspend fun getAllFilteredCharacters(filterMap: Map<String, String>): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 60,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            remoteMediator = SearchRemoteMediator(
                characterDao = characterDAO,
                locationShortInfoDao = locationShortInfoDAO,
                remoteKeyDao = remoteKeyDao,
                service = service,
                filter = filterMap,
                database = database,
            ),
            pagingSourceFactory = {
                characterDAO.getAllCharactersWithFilter(
                    name = filterMap["name"] ?: "",
                    status = filterMap["status"] ?: "",
                    species = filterMap["species"] ?: "",
                    type = filterMap["type"] ?: "",
                    gender = filterMap["gender"] ?: "",
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getCharacterById(id: Long): Flow<Character> = characterDAO.getCharacterById(id).map { it.toDomain() }

    override suspend fun tryUpdateInfoAboutCharacter(id: Long) {
        try {
            val response = service.getCharacterById(id)

            if (response.isSuccessful) {
                response.body()?.toRoomEntity()?.let { characterDAO.insert(it) }
            }
        } catch (e: Exception) {
            Log.e("API request error", e.message.toString())
        }
    }
}