package ru.easycode.zerotoheroandroidtdd

sealed interface ProgressUi {
    data class Initial(val connected: Boolean) : ProgressUi
    object Loading : ProgressUi
    data class Data(val value: String) : ProgressUi
}