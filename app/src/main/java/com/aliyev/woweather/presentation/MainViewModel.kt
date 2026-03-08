package com.aliyev.woweather.presentation

import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.domain.repository.networkObserve.ConnectivityObserver
import com.aliyev.woweather.domain.useCase.local.ConnectivityObserverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectivityUseCase: ConnectivityObserverUseCase,
) : BaseViewModel<MainUiState, MainUiEffect>() {

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityUseCase.observer().collectLatest {
                setState(MainUiState.Result(it))
            }
        }
    }


}

sealed class MainUiState : State {
    data class Result(val data: ConnectivityObserver.Status) : MainUiState()
}

sealed interface MainUiEffect : Effect {
    data class ShowMessage(val message: String) : MainUiEffect
}
