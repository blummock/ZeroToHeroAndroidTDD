package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable

sealed interface UiState : Serializable {

    val text: String

    fun map(textView: TextView, button: Button)

    data class Base(override val text: String) : UiState {
        override fun map(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = true
        }
    }

    data class Max(override val text: String) : UiState {
        override fun map(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }
    }
}