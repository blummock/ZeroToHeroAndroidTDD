package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState("Hello World!"))
    val state = _state.asStateFlow()

    fun setText(text: String) {
        _state.update { it.copy(text = text) }
    }
}