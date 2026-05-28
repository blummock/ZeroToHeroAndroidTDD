package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProgressViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val runAsync: RunAsync,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<ProgressUi>(ProgressUi.Initial)
    val state: StateFlow<ProgressUi> = _state

    init {
        savedStateHandle.get<String>(KEY)?.let {
            _state.value = ProgressUi.Data(it)
        }
    }

    fun load() {
        _state.value = ProgressUi.Loading
    }

    fun loadInternal() {
        runAsync.runAsync(
            viewModelScope,
            background = { repository.load() },
            ui = { result ->
                _state.value = ProgressUi.Data(result)
                savedStateHandle[KEY] = result
            }
        )
    }

    companion object {
        private const val KEY = "data"
    }
}