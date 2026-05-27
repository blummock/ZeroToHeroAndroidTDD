package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var counter: Count by rememberSaveable {
                        mutableStateOf(
                            Count.Base(
                                min = 0,
                                max = 2,
                                value = 1,
                                step = 1
                            )
                        )
                    }
                    Row {
                        Button(
                            onClick = { counter = counter.decrement() },
                            enabled = !counter.isMin(),
                        ) {
                            Text(text = "-")
                        }
                        Text(
                            text = counter.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = { counter = counter.increment() },
                            enabled = !counter.isMax(),
                        ) {
                            Text(text = "+")
                        }
                    }
                }
            }
        }
    }
}