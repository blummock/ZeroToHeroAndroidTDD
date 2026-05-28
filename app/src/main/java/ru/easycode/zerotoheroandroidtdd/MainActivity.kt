package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
                    var email by rememberSaveable { mutableStateOf("") }
                    val isValid by remember { derivedStateOf { EmailValidator.Base.isValid(email) } }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        OutlinedTextField(
                            value = email,
                            onValueChange = { value -> email = value },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("emailInputTag"),
                            singleLine = true,
                        )

                        Button(
                            onClick = {},
                            enabled = isValid,
                            modifier = Modifier.testTag("loginButtonTag")
                        ) {
                            Text("login")
                        }
                    }
                }
            }
        }
    }
}