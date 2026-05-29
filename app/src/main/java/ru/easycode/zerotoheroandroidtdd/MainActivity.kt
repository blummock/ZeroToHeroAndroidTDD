package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {

    private val db by lazy(LazyThreadSafetyMode.NONE) {
        Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    private val viewModel: ListViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return ListViewModel(
                        dao = db.dao(),
                        provideTime = ProvideTime.Base(),
                        runAsync = RunAsync.Base()
                    ) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    var inputText by rememberSaveable { mutableStateOf("") }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = inputText,
                            onValueChange = { newText -> inputText = newText },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("textField")
                        )

                        Button(
                            onClick = {
                                viewModel.add(inputText)
                                inputText = ""
                            },
                            modifier = Modifier
                                .testTag("addButton")
                        ) {
                            Text(text = "Add Item")
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .testTag("ListLazyColumn")
                        ) {
                            itemsIndexed(state.asReversed()) { index, item ->
                                Text(
                                    text = item.text,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .testTag("Element at $index")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}