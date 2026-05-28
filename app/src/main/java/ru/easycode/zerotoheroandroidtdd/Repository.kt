package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

interface Repository {
    suspend fun load(): String

    class Base(private val dispatcher: CoroutineDispatcher) : Repository {
        override suspend fun load(): String = withContext(dispatcher) {
            delay(1000)
            "Success!"
        }
    }
}