package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val runAsync: RunAsync,
    private val repository: Repository,
    connection: MonitorConnection,
) : ViewModel() {

    private val state = MutableStateFlow<ProgressUi>(ProgressUi.Initial)
    val stateFlow: StateFlow<ProgressUi> = state.asStateFlow()

    init {
        savedStateHandle.get<String>(KEY)?.let {
            state.value = ProgressUi.Data(it)
        }
        viewModelScope.launch(Dispatchers.IO) {
            connection.connectedFlow()
                .collect { connected ->
                    when (state.value) {
                        is ProgressUi.Data -> Unit
                        ProgressUi.Initial,
                        ProgressUi.Connected,
                        ProgressUi.Disconnected -> {
                            state.value = if (connected) ProgressUi.Connected else ProgressUi.Disconnected
                        }

                        ProgressUi.Loading -> {
                            if (!connected) {
                                state.value = ProgressUi.Disconnected
                            }
                        }
                    }
                }
        }
    }

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