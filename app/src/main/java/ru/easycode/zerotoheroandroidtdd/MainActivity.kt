package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProvider(owner = this, factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(LiveDataWrapper.Base(), Repository.Base()) as T
            }
        })[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val title = findViewById<TextView>(R.id.titleTextView)
        val loadButton = findViewById<Button>(R.id.actionButton)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        loadButton.setOnClickListener {
            viewModel.load()
        }
        viewModel.state.observe(this) {
            it.setView(title, loadButton, progress)
        }
    }
}