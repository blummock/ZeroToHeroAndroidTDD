package ru.easycode.zerotoheroandroidtdd

sealed interface ProgressUi {
    data object Empty : ProgressUi
    data object Loading : ProgressUi
    data object Disconnected : ProgressUi
    data object Connected : ProgressUi
    data class Data(val value: String) : ProgressUi
}