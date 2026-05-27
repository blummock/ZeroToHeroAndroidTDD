package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val liveDataWrapper: LiveDataWrapper,
    private val repository: Repository
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    init {
        addCloseable { viewModelScope.cancel() }
    }

    val state: LiveData<UiState>
        get() = liveDataWrapper.liveData()

    fun load() {
        viewModelScope.launch {
            liveDataWrapper.update(UiState.ShowProgress)
            withContext(Dispatchers.IO) {
                repository.load()
            }
            liveDataWrapper.update(UiState.ShowData)
        }
    }
}