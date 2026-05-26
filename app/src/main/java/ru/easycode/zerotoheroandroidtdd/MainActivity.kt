package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val title = findViewById<TextView>(R.id.titleTextView)
        val loadButton = findViewById<Button>(R.id.actionButton)
        val progress = findViewById<ProgressBar>(R.id.progressBar)
        loadButton.setOnClickListener {
            loadButton.isEnabled = false
            progress.visibility = View.VISIBLE
            loadButton.postDelayed({
                title.visibility = View.VISIBLE
                progress.visibility = View.GONE
                loadButton.isEnabled = true
            }, 3000)
        }
    }
}