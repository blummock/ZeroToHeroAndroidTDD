package ru.easycode.zerotoheroandroidtdd


import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.removeButton)
        val textView: TextView? = findViewById(R.id.titleTextView)
        val root = findViewById<LinearLayout>(R.id.rootLayout)

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            viewModel.state
                .map { it.removed }
                .collect {
                    if (it) {
                        textView?.let { view -> root.removeView(view) }
                    }
                }
        }
        button.setOnClickListener {
            viewModel.hideText()
        }
    }
}