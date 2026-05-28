package ru.easycode.zerotoheroandroidtdd

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val uiState by viewModel.stateFlow.collectAsState()
        when (val state = uiState) {
            is ProgressUi.Initial -> {
                Column {
                    if (!state.connected) {
                        Text(
                            text = "No internet connection",
                            modifier = Modifier.testTag("noInternetConnection")
                        )
                    }
                    Button(
                        modifier = Modifier
                            .testTag("loadButton")
                            .wrapContentSize(),
                        enabled = state.connected,
                        onClick = {
                            viewModel.load()
                            viewModel.loadInternal()
                        }
                    ) {
                        Text("Load")
                    }
                }
            }

            ProgressUi.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("progress")
                )
            }

            is ProgressUi.Data -> {
                Text(
                    modifier = Modifier.testTag("result"),
                    text = state.value
                )
            }
        }
    }
}