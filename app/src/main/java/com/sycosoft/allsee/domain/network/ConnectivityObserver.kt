package com.sycosoft.allsee.domain.network

import kotlinx.coroutines.flow.Flow

/**
 * Represents an observer and returns a Flow that emits the current network status whenever it changes
 *
 * @return A [Flow] emitting the network [Status] updates.
 *
 * @see Status
 *
 * @author Jamie-Rhys Edwards
 * @since v0.0.1
 */
interface ConnectivityObserver {
    fun observe(): Flow<Status>

    /**
     * Status of the network connection at any given time.
     *
     * Available - Network is available, connected and stable.
     * Unavailable - Network is unavailable or disconnected.
     * Losing - Network is connected but unstable and may disconnect.
     * Lost - Network connection has been lost.
     */
    enum class Status {
        Available,
        Unavailable,
        Losing,
        Lost,
    }
}