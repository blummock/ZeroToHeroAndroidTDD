package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(
        UIState(
            listItems = savedStateHandle[KEY] ?: emptyList(),
            text = ""
        )
    )
    val state = _state.asStateFlow()

    fun updateText(text: String) {
        _state.update { currentState ->
            currentState.copy(text = text)
        }
    }

    fun add(text: String) {
        if (text.isBlank()) return
        _state.update { currentState ->
            currentState.copy(text = "", listItems = currentState.listItems + text)
        }
        savedStateHandle[KEY] = _state.value.listItems
    }

    private companion object {
        private const val KEY = "list_key"
    }
}