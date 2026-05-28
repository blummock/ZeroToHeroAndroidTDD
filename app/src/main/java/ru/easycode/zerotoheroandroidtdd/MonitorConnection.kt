package ru.easycode.zerotoheroandroidtdd

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

interface MonitorConnection {

    fun connectedFlow(): Flow<Boolean>

    class Base(private val dispatcher: CoroutineDispatcher, private val cm: ConnectivityManager) : MonitorConnection {

        override fun connectedFlow() = callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    trySend(false)
                }
            }

            val request = NetworkRequest.Builder().build()

            cm.registerNetworkCallback(request, callback)

            awaitClose {
                cm.unregisterNetworkCallback(callback)
            }
        }.flowOn(dispatcher)
    }
}