package com.sergiom.domain.usecases

import androidx.paging.PagingData
import com.sergiom.data.models.CharacterModel
import com.sergiom.data.repository.NetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPaginationCharactersUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<CharacterModel>>
}

class GetPaginationCharactersUseCaseImpl @Inject constructor(
    private val repository: NetRepository
): GetPaginationCharactersUseCase  {
    override suspend fun invoke(query: String): Flow<PagingData<CharacterModel>> =
        repository.getSearchedCharactersNextPage(query = query)

}