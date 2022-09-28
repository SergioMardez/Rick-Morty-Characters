package com.sergiom.domain.usecases

import com.sergiom.data.models.CharacterListModel
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.*
import javax.inject.Inject

interface GetCharactersUseCase {
    suspend operator fun invoke(): Either<CharacterListModel, String>
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: NetRepository
): GetCharactersUseCase {
    override suspend fun invoke(): Either<CharacterListModel, String> {
        val result = repository.getCharacters()
        result.onSuccess {
            if(it.results.isEmpty()) return eitherFailure("Empty error")
            return eitherSuccess(it)
        }.onFailure {
            return eitherFailure(it)
        }
        return eitherFailure("Undefined Error")
    }
}