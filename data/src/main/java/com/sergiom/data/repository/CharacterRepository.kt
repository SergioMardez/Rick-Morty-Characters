package com.sergiom.data.repository

import com.sergiom.data.local.CharactersDao
import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.data.utils.performGetItem
import com.sergiom.data.utils.performGetItems
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val localDataSource: CharactersDao,
    private val mapper: Mapper<CharacterModel, CharacterDataBaseModel>
) {
    suspend fun saveCharacterToDatabase(character: CharacterModel) {
        localDataSource.insert(
            mapper.map(character)
        )
    }

    fun getCharacterFromDataBase(characterId: Int) = performGetItem( databaseQuery = { localDataSource.getCharacter(characterId) })

    fun getCharactersFromDataBase() = performGetItems( databaseQuery = { localDataSource.getAllCharacters() })

    fun deleteAllCharacters() = localDataSource.deleteAllCharacters()
}