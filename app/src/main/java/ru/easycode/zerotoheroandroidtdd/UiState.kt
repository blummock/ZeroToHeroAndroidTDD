package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

sealed interface UiState {

    fun setView(textView: TextView, button: Button, progressBar: ProgressBar)

    data object ShowProgress : UiState {
        override fun setView(
            textView: TextView,
            button: Button,
            progressBar: ProgressBar
        ) {
            progressBar.visibility = View.VISIBLE
            button.isEnabled = false
        }

    }

    data object ShowData : UiState {
        override fun setView(
            textView: TextView,
            button: Button,
            progressBar: ProgressBar
        ) {
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            button.isEnabled = true
        }

    }
}