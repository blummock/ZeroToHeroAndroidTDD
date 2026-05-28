package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import kotlinx.coroutines.delay
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
                    var state: UIState by rememberSaveable { mutableStateOf(UIState.Default) }

                    LaunchedEffect(state) {
                        if (state is UIState.Progress) {
                            delay(2000)
                            state = UIState.Success
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when (state) {
                            UIState.Default -> {
                                Button(
                                    modifier = Modifier.testTag("loadButton"),
                                    onClick = {
                                        state = UIState.Progress
                                    }
                                ) {
                                    Text("CLick")
                                }
                            }

                            UIState.Progress -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.testTag("progress"),
                                )
                            }

                            UIState.Success -> {
                                Text(
                                    modifier = Modifier.testTag("result"),
                                    text = "Success!"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}