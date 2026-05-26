package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.hideButton)
        val textView = findViewById<TextView>(R.id.titleTextView)
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            viewModel.state
                .collect {
                    textView.visibility = if (it.hidden) View.GONE else View.VISIBLE
                }
        }
        button.setOnClickListener {
            viewModel.hideText()
        }
    }
}