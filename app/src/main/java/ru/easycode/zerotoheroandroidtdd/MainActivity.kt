package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    val colorCorrect by remember {
                        derivedStateOf {
                            when (state) {
                                UIState.Correct -> Color.Green
                                UIState.Default -> Color.Yellow
                                UIState.Incorrect -> Color.Green
                            }
                        }
                    }
                    val colorIncorrect by remember {
                        derivedStateOf {
                            when (state) {
                                UIState.Correct -> Color.Gray
                                UIState.Default -> Color.Yellow
                                UIState.Incorrect -> Color.Red
                            }
                        }
                    }

                    Column {
                        Text(
                            text = "some question"
                        )
                        Button(
                            onClick = {
                                state = UIState.Correct
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorCorrect
                            )
                        ) {
                            Text("correct")
                        }
                        Button(
                            onClick = {
                                state = UIState.Incorrect
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorIncorrect
                            )
                        ) {
                            Text("incorrect")
                        }
                    }
                }
            }
        }
    }
}