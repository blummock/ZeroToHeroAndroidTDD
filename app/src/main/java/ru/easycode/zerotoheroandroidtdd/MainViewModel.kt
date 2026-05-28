package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val runAsync: RunAsync,
    private val repository: Repository,
    connection: MonitorConnection,
) : ViewModel() {

    private val state = MutableStateFlow<ProgressUi>(ProgressUi.Initial(false))
    val stateFlow: StateFlow<ProgressUi> = state.combine(connection.connectedFlow()) { state, connected ->
        if (state is ProgressUi.Initial) {
            state.copy(connected = connected)
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(300), ProgressUi.Initial(false))


    fun load() {
        state.value = ProgressUi.Loading
    }

    fun loadInternal() {
        runAsync.runAsync(
            viewModelScope,
            background = { repository.load() },
            ui = { result ->
                state.value = ProgressUi.Data(result)
                savedStateHandle[KEY] = result
            }
        )
    }

    private companion object {
        private const val KEY = "data"
    }
}