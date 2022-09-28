package com.sergiom.rickmortycharacters.ui.charactersearchview

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.data.models.CharacterModel
import com.sergiom.data.utils.onFailure
import com.sergiom.data.utils.onSuccess
import com.sergiom.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val saveCharacterToDataBaseUseCase: SaveCharacterToDataBaseUseCase,
    private val getPaginationCharactersUseCase: GetPaginationCharactersUseCase
): ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state.asStateFlow()

    var query = ""

    init {
        Handler(Looper.getMainLooper()).postDelayed({
            _state.update {
                it.copy(
                    loading = false
                )
            }
        }, 1000) //To see logo image like a splash
    }

    fun saveCharacter(characterModel: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveCharacterToDataBaseUseCase.invoke(characterModel)
        }
    }

    suspend fun getNextPage(query: String): Flow<PagingData<CharacterModel>> {
        return getPaginationCharactersUseCase.invoke(query).map { pagingData ->
            pagingData.map {
                it
            }
        }.cachedIn(viewModelScope)
    }

    data class State(
        var loading: Boolean = true,
        var error: String? = null
    )

}