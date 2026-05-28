package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface RunAsync {
    fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    )

    class Base(private val dispatcher: CoroutineDispatcher) : RunAsync {
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            background: suspend () -> T,
            ui: (T) -> Unit
        ) {
            scope.launch(dispatcher) {
                val result = background.invoke()
                ui.invoke(result)
            }
        }
    }
}