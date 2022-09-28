package com.sergiom.rickmortycharacters

import com.google.gson.Gson
import com.sergiom.data.models.CharacterListModel
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.Either
import com.sergiom.data.utils.eitherSuccess
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import com.sergiom.domain.usecases.GetCharactersUseCase
import com.sergiom.domain.usecases.GetCharactersUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseTest {
    @Mock
    lateinit var mockNetRepo: NetRepository

    lateinit var useCaseToTest: GetCharactersUseCase

    lateinit var mockCharacters: CharacterListModel

    @Before
    fun setup() {
        mockCharacters = Gson().fromJson(characters, CharacterListModel::class.java)
        useCaseToTest = GetCharactersUseCaseImpl(mockNetRepo)
    }

    @Test
    fun `get net data and success`()  {
        runBlocking {
            Mockito.`when`(mockNetRepo.getCharacters()).thenReturn(eitherSuccess(mockCharacters))
            assert(useCaseToTest() is Either.Success)
            useCaseToTest.invoke().onSuccess {
                assert(it.results.isNotEmpty())
                assert(it.results.size == 3)
                assert(it.results.first().name == "Rick Sanchez")
            }
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            assert(useCaseToTest() is Either.Failure)
            useCaseToTest.invoke().onFailure {
                assert(it.isNotEmpty())
            }
        }
    }

}