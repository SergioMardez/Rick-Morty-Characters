package com.sergiom.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sergiom.data.datasource.CharacterDataSource
import com.sergiom.data.extensions.errorsHandle
import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.CharacterListModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.data.net.RestClient
import com.sergiom.data.net.response.CharacterEntity
import com.sergiom.data.net.response.CharacterListEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.Either
import com.sergiom.data.utils.eitherFailure
import com.sergiom.data.utils.eitherSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetRepositoryImpl @Inject constructor(
    private val restClient: RestClient,
    private val dataSource: CharacterDataSource,
    private val mapper: @JvmSuppressWildcards Mapper<CharacterListEntity, CharacterListModel>,
    private val mapperCharacter: @JvmSuppressWildcards Mapper<CharacterEntity, CharacterModel>
): NetRepository {

    override suspend fun getCharacters(): Either<CharacterListModel, String> {
        try {
            val result = restClient.getRemoteCaller().getCharacters()
            result.let {
                return eitherSuccess(mapper.map(it.errorsHandle()))
            }
        } catch (e: Exception) {
            return eitherFailure(e.toString())
        }
    }

    override suspend fun getSearchedCharacters(query: String): Either<CharacterListModel, String> {
        try {
            val result = restClient.getRemoteCaller().getSearchedCharacters(query)
            result.let {
                return eitherSuccess(mapper.map(it.errorsHandle()))
            }
        } catch (e: Exception) {
            return eitherFailure(e.toString())
        }
    }

    override suspend fun getSearchedCharactersNextPage(query: String): Flow<PagingData<CharacterModel>> =
        Pager(
            PagingConfig(pageSize = 20, initialLoadSize = 20)
        ) {
            dataSource.apply {
                this.query = query
            }
        }.flow.map { pagingData ->
            pagingData.map {
                mapperCharacter.map(it)
            }
        }

}