package com.sergiom.domain.usecases

import androidx.lifecycle.LiveData
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.repository.CharacterRepository
import javax.inject.Inject

interface GetCharacterFromDataBaseUseCase {
    fun invoke(characterId: Int): LiveData<CharacterDataBaseModel>
}

class GetCharacterFromDataBaseUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
): GetCharacterFromDataBaseUseCase {
    override fun invoke(characterId: Int): LiveData<CharacterDataBaseModel> =
        repository.getCharacterFromDataBase(characterId)
}