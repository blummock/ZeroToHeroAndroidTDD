package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProvider(owner = this, factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainActivityViewModel(counter = Count.Base(2)) as T
            }
        })[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.incrementButton)
        val textView = findViewById<TextView>(R.id.countTextView)

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            viewModel.state
                .map { it.counter }
                .collect {
                    textView.text = it
                }
        }
        button.setOnClickListener {
            viewModel.increment()
        }
    }
}