package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

sealed interface UIState : Serializable {

    data object Correct : UIState

    data object Incorrect : UIState

    data object Default : UIState
}