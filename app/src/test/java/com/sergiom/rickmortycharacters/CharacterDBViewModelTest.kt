package com.sergiom.rickmortycharacters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.repository.CharacterRepository
import com.sergiom.domain.usecases.DeleteAllCharacterUseCase
import com.sergiom.domain.usecases.GetCharactersFromDataBaseUseCase
import com.sergiom.rickmortycharacters.ui.charactersdbview.CharacterDBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterDBViewModelTest {

    @Mock
    lateinit var getCharactersDBUseCase: GetCharactersFromDataBaseUseCase

    @Mock
    lateinit var deleteCartUseCase: DeleteAllCharacterUseCase

    @Mock
    lateinit var characterRepository: CharacterRepository

    lateinit var characterDBViewModel: CharacterDBViewModel

    lateinit var mockCharacterDBData: List<CharacterDataBaseModel>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val dispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockCharacterDBData = Gson().fromJson(characterDB, Array<CharacterDataBaseModel>::class.java).toList()
        mockCartViewModel()
    }


    @Test
    fun `init viewModel test`() {
        runBlocking {
            assert(characterDBViewModel.state.value.characters.value!!.isNotEmpty())
        }
    }

    @Test
    fun `get characters data and success`()  {
        runBlocking {
            assert(characterDBViewModel.state.value.characters.value!!.isNotEmpty())
            assert(characterDBViewModel.state.value.characters.value!!.last().name == "Summer Smith")
        }
    }

    private fun mockCartViewModel() {
        val liveData = MutableLiveData<List<CharacterDataBaseModel>>()
        liveData.value = mockCharacterDBData
        Mockito.`when`(characterRepository.getCharactersFromDataBase()).thenAnswer {
            liveData
        }
        Mockito.`when`(getCharactersDBUseCase.invoke()).thenAnswer {
            characterRepository.getCharactersFromDataBase()
        }
        characterDBViewModel = CharacterDBViewModel(
            getCharactersFromDataBaseUseCase = getCharactersDBUseCase,
            deleteAllCharacterUseCase = deleteCartUseCase
        )
    }

}