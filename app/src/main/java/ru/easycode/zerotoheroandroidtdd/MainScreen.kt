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
            ProgressUi.Connected,
            ProgressUi.Disconnected,
            ProgressUi.Initial -> {
                Column {
                    if (state is ProgressUi.Disconnected) {
                        Text(
                            text = "No internet connection",
                            modifier = Modifier.testTag("noInternetConnection")
                        )
                    }
                    Button(
                        modifier = Modifier
                            .testTag("loadButton")
                            .wrapContentSize(),
                        enabled = state is ProgressUi.Connected,
                        onClick = {
                            viewModel.load()
                            viewModel.loadInternal()
                        }
                    ) {
                        Text("Load")
                    }
                }
            }

            is ProgressUi.Data -> {
                Text(
                    modifier = Modifier.testTag("result"),
                    text = state.value
                )
            }

            ProgressUi.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("progress")
                )
            }
        }
    }
}