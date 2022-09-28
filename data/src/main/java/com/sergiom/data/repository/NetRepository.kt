package com.sergiom.data.repository

import androidx.paging.PagingData
import com.sergiom.data.models.CharacterListModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.data.utils.Either
import kotlinx.coroutines.flow.Flow

interface NetRepository {
    suspend fun getCharacters(): Either<CharacterListModel, String>
    suspend fun getSearchedCharacters(query: String): Either<CharacterListModel, String>
    suspend fun getSearchedCharactersNextPage(query: String): Flow<PagingData<CharacterModel>>
}