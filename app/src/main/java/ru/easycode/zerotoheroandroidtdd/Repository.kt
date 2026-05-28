package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.delay

interface Repository {
    suspend fun load(): String

    class Base : Repository {
        override suspend fun load(): String {
            delay(1000)
            return "Success!"
        }
    }
}