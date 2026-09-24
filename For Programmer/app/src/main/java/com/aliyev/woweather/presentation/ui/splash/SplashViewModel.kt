package com.aliyev.woweather.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.domain.useCase.local.ConnectivityObserverUseCase
import com.aliyev.woweather.domain.useCase.local.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val connectivityUseCase: ConnectivityObserverUseCase,
) : BaseViewModel<SplashUiState, SplashUiEffect>() {

    init {
        isNetworkAvailable()
    }

    fun getIsCitySelected() {
        viewModelScope.launch {
            dataStoreUseCase.getIsCitySelected().collectLatest {
                setState(SplashUiState.IsCitySelected(it ?: false))
            }
        }
    }

    private fun isNetworkAvailable() {
        val connectionStatus = connectivityUseCase.isNetworkAvailable()
        setState(SplashUiState.IsNetworkAvailable(connectionStatus))
    }

}

sealed interface SplashUiState : State {

    data class IsNetworkAvailable(val data: Boolean) : SplashUiState

    data class IsCitySelected(val isSelected: Boolean) : SplashUiState

}

sealed interface SplashUiEffect : Effect {

    data class ShowMessage(val message: String) : SplashUiEffect

}