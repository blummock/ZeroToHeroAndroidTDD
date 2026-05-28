package ru.easycode.zerotoheroandroidtdd

sealed interface ProgressUi {
    data object Initial : ProgressUi
    data object Connected : ProgressUi
    data object Disconnected : ProgressUi
    data object Loading : ProgressUi
    data class Data(val value: String) : ProgressUi
}