package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val runAsync: RunAsync,
    private val repository: Repository,
    connection: MonitorConnection,
) : ViewModel() {

    private val state = MutableStateFlow<ProgressUi>(ProgressUi.Empty)
    val stateFlow: StateFlow<ProgressUi> = state.asStateFlow()

    init {
        savedStateHandle.get<String>(KEY)?.let {
            state.value = ProgressUi.Data(it)
        }
        runAsync.runFlow(
            scope = viewModelScope,
            flow = connection.connectedFlow(),
            onEach = { connected ->
                when (state.value) {
                    ProgressUi.Connected -> update(true, connected)
                    is ProgressUi.Data -> Unit
                    ProgressUi.Disconnected -> update(false, connected)
                    ProgressUi.Empty -> initial(connected)
                    ProgressUi.Loading -> update(true, connected)
                }
            }
        )
    }

    fun initial(connected: Boolean) {
        state.value = if (connected) ProgressUi.Connected else ProgressUi.Disconnected
    }

    fun update(alreadyConnected: Boolean, connected: Boolean) {
        if (alreadyConnected == connected) return
        state.value = if (connected) ProgressUi.Connected else ProgressUi.Disconnected
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