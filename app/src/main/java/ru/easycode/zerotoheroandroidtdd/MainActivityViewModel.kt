package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel(
    private val counter: Count
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState(counter = "0"))
    val state = _state.asStateFlow()

    fun increment() {
        _state.update {
            runCatching {
                it.copy(counter = counter.increment(it.counter))
            }.getOrDefault(it)
        }
    }
}