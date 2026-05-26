package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView
import java.io.Serializable

interface UiState : Serializable {

    val text: String

    fun setView(texView: TextView, incrementButton: Button, decrementButton: Button)

    data class Base(override val text: String) : UiState {
        override fun setView(
            texView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            texView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = true
        }
    }

    data class Max(override val text: String) : UiState {
        override fun setView(
            texView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            texView.text = text
            incrementButton.isEnabled = false
            decrementButton.isEnabled = true
        }
    }

    data class Min(override val text: String) : UiState {
        override fun setView(
            texView: TextView,
            incrementButton: Button,
            decrementButton: Button
        ) {
            texView.text = text
            incrementButton.isEnabled = true
            decrementButton.isEnabled = false
        }
    }
}