package com.example.codingchallengeimgur.ui.common.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}


@OptIn(ExperimentalCoroutinesApi::class)
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(ConnectivityManager::class.java)

    val callback = NetworkCallback { connectionState -> trySend(connectionState) }

    connectivityManager.registerDefaultNetworkCallback(callback)
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)
    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
    var currentState: ConnectionState = ConnectionState.Unavailable
    connectivityManager.allNetworks.forEach { network ->
        val networkCapability = connectivityManager.getNetworkCapabilities(network)
        networkCapability?.let {
            if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                currentState = ConnectionState.Available
                return@forEach
            }
        }
    }
    return currentState
}

@Suppress("FunctionName")
private fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}