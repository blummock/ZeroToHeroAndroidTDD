package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy(mode = LazyThreadSafetyMode.NONE) {
        ViewModelProvider(owner = this, factory = object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return ProgressViewModel(
                    savedStateHandle = handle,
                    runAsync = RunAsync.Base(Dispatchers.IO),
                    repository = Repository.Base()
                ) as T
            }
        })[ProgressViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.state.collectAsState()
                    when (val state = uiState) {
                        ProgressUi.Initial -> {
                            Button(
                                modifier = Modifier.testTag("loadButton").wrapContentSize(),
                                onClick = {
                                    viewModel.load()
                                    viewModel.loadInternal()
                                }
                            ) {
                                Text("Load")
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
        }
    }
}