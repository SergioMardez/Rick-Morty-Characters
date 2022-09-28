package com.sergiom.rickmortycharacters.ui.characterdetailview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.domain.usecases.GetCharacterFromDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterFromDataBaseUseCase: GetCharacterFromDataBaseUseCase
): ViewModel(){

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    character = getCharacterFromDataBaseUseCase.invoke(characterId)
                )
            }
        }
    }

    data class State(
        var character: LiveData<CharacterDataBaseModel>? = null,
    )
}