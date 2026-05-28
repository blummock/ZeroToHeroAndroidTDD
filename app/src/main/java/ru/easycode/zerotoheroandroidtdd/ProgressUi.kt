package ru.easycode.zerotoheroandroidtdd

sealed interface ProgressUi {
    object Initial : ProgressUi
    object Loading : ProgressUi
    data class Data(val value: String) : ProgressUi
}