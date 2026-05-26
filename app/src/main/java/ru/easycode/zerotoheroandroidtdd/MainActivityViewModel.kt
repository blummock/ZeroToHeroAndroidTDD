package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState(false))
    val state = _state.asStateFlow()

    fun removeView() {
        _state.update { it.copy(removed = true) }
    }
}