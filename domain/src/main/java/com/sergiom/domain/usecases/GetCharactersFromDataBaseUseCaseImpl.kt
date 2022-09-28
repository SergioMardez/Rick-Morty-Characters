package com.sergiom.domain.usecases

import androidx.lifecycle.LiveData
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.repository.CharacterRepository
import javax.inject.Inject

interface GetCharactersFromDataBaseUseCase {
    fun invoke(): LiveData<List<CharacterDataBaseModel>>
}

class GetCharactersFromDataBaseUseCaseImpl  @Inject constructor(
    private val repository: CharacterRepository
): GetCharactersFromDataBaseUseCase {
    override fun invoke(): LiveData<List<CharacterDataBaseModel>> =
        repository.getCharactersFromDataBase()
}