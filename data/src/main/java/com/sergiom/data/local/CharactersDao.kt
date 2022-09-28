package com.sergiom.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergiom.data.models.CharacterDataBaseModel

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<CharacterDataBaseModel>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<CharacterDataBaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterDataBaseModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterDataBaseModel)

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()
}