package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

interface UIState : Serializable {

    data object Default : UIState
    data object Success : UIState
    data object Progress : UIState
}