package com.aliyev.woweather.domain.useCase.local

import com.aliyev.woweather.domain.repository.networkObserve.ConnectivityObserver
import javax.inject.Inject

class ConnectivityObserverUseCase @Inject constructor(
    private val co: ConnectivityObserver,
) {

    fun observer() = co.observe()

    fun isNetworkAvailable() = co.isNetworkAvailable()

}