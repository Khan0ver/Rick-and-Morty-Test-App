package com.example.rickandmortytestapp.features.search.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortytestapp.features.search.data.room.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Insert(
        entity = CharacterEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("""
            SELECT * FROM characters WHERE 
            (:name IS NULL OR name LIKE '%' || :name  || '%') AND
            (:status IS NULL OR status LIKE '%' || :status  || '%') AND
            (:species IS NULL OR species LIKE '%' || :species  || '%') AND
            (:type IS NULL OR type LIKE '%' || :type  || '%') AND
            (:gender IS NULL OR gender LIKE '%' || :gender  || '%')
            ORDER BY id
    """)
    fun getAllCharactersWithFilter(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Insert(
        entity = CharacterEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Long): Flow<CharacterEntity>
}