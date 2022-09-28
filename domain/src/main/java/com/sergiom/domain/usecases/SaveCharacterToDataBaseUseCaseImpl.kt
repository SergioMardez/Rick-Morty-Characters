package com.sergiom.domain.usecases

import com.sergiom.data.models.CharacterModel
import com.sergiom.data.repository.CharacterRepository
import javax.inject.Inject

interface SaveCharacterToDataBaseUseCase {
    suspend operator fun invoke(character: CharacterModel)
}

class SaveCharacterToDataBaseUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
): SaveCharacterToDataBaseUseCase {
    override suspend fun invoke(character: CharacterModel) {
        repository.saveCharacterToDatabase(character)
    }
}