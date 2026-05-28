package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface RunAsync {
    fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    )

    fun <T : Any> runFlow(
        scope: CoroutineScope,
        flow: Flow<T>,
        onEach: (T) -> Unit
    )

    class Base : RunAsync {
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            background: suspend () -> T,
            ui: (T) -> Unit
        ) {
            scope.launch {
                val result = background.invoke()
                ui.invoke(result)
            }
        }

        override fun <T : Any> runFlow(
            scope: CoroutineScope,
            flow: Flow<T>,
            onEach: (T) -> Unit
        ) {
            scope.launch {
                flow.collect {
                    onEach.invoke(it)
                }
            }
        }
    }
}