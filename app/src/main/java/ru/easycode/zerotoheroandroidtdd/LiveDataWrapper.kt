package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    class Base() : LiveDataWrapper {

        private val liveData: MutableLiveData<UiState> = MutableLiveData()

        override fun update(value: UiState) {
            liveData.value = value
        }

        override fun liveData() = liveData
    }
}