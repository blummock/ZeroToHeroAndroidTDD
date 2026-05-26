package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val count: Count = Count.Base(step = 2, max = 4, min = 0)
    private val state = MutableStateFlow<UiState>(count.initial("0"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        val decrementButton = findViewById<Button>(R.id.decrementButton)
        val textView = findViewById<TextView>(R.id.countTextView)
        incrementButton.setOnClickListener {
            state.update {
                runCatching { count.increment(it.text) }.getOrDefault(it)
            }
        }
        decrementButton.setOnClickListener {
            state.update {
                runCatching { count.decrement(it.text) }.getOrDefault(it)
            }
        }
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            state.collect {
                it.setView(textView, incrementButton, decrementButton)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(KEY, UiState::class.java)
        } else {
            savedInstanceState.getSerializable(KEY) as UiState?
        } ?: count.initial("0")
    }

    private companion object {
        const val KEY = "key"
    }
}