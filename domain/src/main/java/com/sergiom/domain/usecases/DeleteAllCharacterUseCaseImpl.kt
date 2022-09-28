package com.sergiom.domain.usecases

import com.sergiom.data.repository.CharacterRepository
import javax.inject.Inject

interface DeleteAllCharacterUseCase {
    suspend operator fun invoke()
}

class DeleteAllCharacterUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
): DeleteAllCharacterUseCase {
    override suspend fun invoke() {
        repository.deleteAllCharacters()
    }
}