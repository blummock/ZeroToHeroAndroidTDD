package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ListViewModel(
    private val dao: RecordsDao,
    private val provideTime: ProvideTime,
    private val runAsync: RunAsync,
) : ViewModel() {

    val state = dao.list().stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(300), emptyList())

    fun add(text: String) {
        if (text.isBlank()) return
        runAsync.run(
            viewModelScope,
            background = {
                dao.insert(
                    RecordEntity(
                        id = provideTime.now(),
                        text = text
                    )
                )
            }
        )
    }
}