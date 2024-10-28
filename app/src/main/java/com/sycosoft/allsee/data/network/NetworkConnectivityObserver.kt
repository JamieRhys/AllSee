package com.sycosoft.allsee.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.sycosoft.allsee.domain.network.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Implementation of [ConnectivityObserver] that uses [ConnectivityManager] to observer network
 * changes and emits status updates using a [Flow].
 *
 * @param context The application context used to obtain the [ConnectivityManager] system service.
 *
 * @see ConnectivityObserver
 *
 * @author Jamie-Rhys Edwards
 * @since v0.0.1
 */
class NetworkConnectivityObserver(context: Context) : ConnectivityObserver {
    // Retrieves the system's connectivity manager for managing network connections.
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Observes the network connectivity status by registering a network callback and emitting system
     * updates.
     *
     * @return A [Flow] that emits the current [ConnectivityObserver.Status] when the network changes.
     */
    override fun observe(): Flow<ConnectivityObserver.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                super.onUnavailable()

                launch { send(ConnectivityObserver.Status.Unavailable) }
            }

            override fun onLost(network: Network) {
                super.onLost(network)

                launch { send(ConnectivityObserver.Status.Lost) }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                launch { send(ConnectivityObserver.Status.Losing) }
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                launch { send(ConnectivityObserver.Status.Available)}
            }
        }

        // Registers the network callback with the connectivity manager to start monitoring the network.
        connectivityManager.registerDefaultNetworkCallback(callback)
        awaitClose {
            // Awaits cancellation of the flow and unregisters the callback when complete.
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged() // Ensures only the unique status changes are emitted at any one time.
}