package com.aliyev.woweather.domain.repository.networkObserve

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    fun isNetworkAvailable(): Boolean

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

}