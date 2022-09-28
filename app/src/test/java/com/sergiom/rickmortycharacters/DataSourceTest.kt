package com.sergiom.rickmortycharacters

import com.sergiom.data.mappers.Mapper
import com.sergiom.data.models.CharacterListModel
import com.sergiom.data.net.response.CharacterListEntity
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.utils.eitherSuccess
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DataSourceTest {

    @Mock
    lateinit var mockNetRepo: NetRepository

    @Mock
    lateinit var mockCharacterEntity: CharacterListEntity

    @Mock
    lateinit var mapper: Mapper<CharacterListEntity, CharacterListModel>

    @Test
    fun `get data and success`() {
        runBlocking {
            Mockito.`when`(mockNetRepo.getSearchedCharacters("")).thenAnswer {
                eitherSuccess(mapper.map(mockCharacterEntity))
            }
            mockNetRepo.getSearchedCharacters("").onSuccess {
                assert(it == mapper.map(mockCharacterEntity))
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            mockNetRepo.getSearchedCharacters("").onSuccess {
                assert(it.equals(null))
            }.onFailure {
                assert(it.isNotEmpty())
            }
        }
    }

}