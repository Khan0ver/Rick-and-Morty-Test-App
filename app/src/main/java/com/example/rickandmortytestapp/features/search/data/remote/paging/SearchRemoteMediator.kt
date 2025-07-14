package com.example.rickandmortytestapp.features.search.data.remote.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortytestapp.features.search.data.remote.service.SearchCharacterService
import com.example.rickandmortytestapp.features.search.data.room.RickAndMortyAppDatabase
import com.example.rickandmortytestapp.features.search.data.room.dao.CharacterDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.LocationShortInfoDAO
import com.example.rickandmortytestapp.features.search.data.room.dao.RemoteKeyDao
import com.example.rickandmortytestapp.features.search.data.room.entity.CharacterEntity
import com.example.rickandmortytestapp.features.search.data.room.entity.LocationShortInfoEntity
import com.example.rickandmortytestapp.features.search.data.room.entity.RemoteKeyEntity
import com.example.rickandmortytestapp.features.search.data.room.mapper.toRoomEntity

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val query: String = "searchQuery?",
    private val characterDao: CharacterDAO,
    private val locationShortInfoDao: LocationShortInfoDAO,
    private val remoteKeyDao: RemoteKeyDao,
    private val service: SearchCharacterService,
    private val filter: Map<String, String>?,
    private val database: RickAndMortyAppDatabase,
) : RemoteMediator<Int, CharacterEntity>() {

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.SKIP_INITIAL_REFRESH
//    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.remoteKeyByQuery(query)
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                }
            }

            val regex = Regex("""(\d+)$""")
            val page = regex.find(loadKey ?: "1")?.groupValues?.get(1)?.toLongOrNull()

            val response = filter?.let {
                service.getFilteredCharacters(filterMap = filter, page = page ?: 1)
            } ?: service.getAllCharactersWithPaging(page = page ?: 1)

            if (response.isSuccessful) {
                if (response.body() != null) {
                    response.body()!!.let {
                        Log.d("RemoteMediator", "Loading page: $page, nextKey: ${it.infoDTO.next}")

                        database.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                remoteKeyDao.deleteByQuery(query)
                            }
                            remoteKeyDao.insertOrReplace(RemoteKeyEntity(query, it.infoDTO.next))

                            it.characters.forEach { character ->
                                locationShortInfoDao.insert(
                                    character.locationShortInfoDTO?.toRoomEntity()
                                        ?: LocationShortInfoEntity()
                                )
                            }

                            characterDao.insertAll(it.characters.map { character ->
                                character.toRoomEntity()
                            })
                        }

                        if (response.body()!!.infoDTO.next == null) MediatorResult.Success(
                            endOfPaginationReached = true
                        ) else {
                            MediatorResult.Success(endOfPaginationReached = false)
                        }
                    }
                } else {
                    throw Exception("API error: ${response.code()} ${response.message()}")
                }
            } else {
                if (response.code() == 404) {
                    MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    throw Exception("API error: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            Log.e("REMOTE MEDIATOR ERROR", e.message.toString())
            MediatorResult.Error(e)
        }
    }
}