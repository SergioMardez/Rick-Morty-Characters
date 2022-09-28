package com.sergiom.rickmortycharacters.ui.charactersdbview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiom.data.models.CharacterDataBaseModel
import com.sergiom.domain.usecases.DeleteAllCharacterUseCase
import com.sergiom.domain.usecases.GetCharactersFromDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDBViewModel @Inject constructor(
    private val getCharactersFromDataBaseUseCase: GetCharactersFromDataBaseUseCase,
    private val deleteAllCharacterUseCase: DeleteAllCharacterUseCase
): ViewModel() {

    private var _state = MutableStateFlow(State(characters = getCharactersFromDataBaseUseCase.invoke()))
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun deleteAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllCharacterUseCase.invoke()
        }
    }

    data class State(
        var characters: LiveData<List<CharacterDataBaseModel>>
    )
}