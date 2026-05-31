package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

interface RunAsync {
    fun <T : Any> runFlowCollect(
        scope: CoroutineScope,
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    )

    class Base(private val dispatcher: CoroutineDispatcher) : RunAsync {
        override fun <T : Any> runFlowCollect(
            scope: CoroutineScope,
            flow: Flow<T>,
            collect: suspend (T) -> Unit
        ) {
            scope.launch {
                flow.flowOn(dispatcher).collect {
                    collect.invoke(it)
                }
            }
        }
    }
}