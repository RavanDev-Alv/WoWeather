package com.aliyev.woweather.presentation.ui.hourly

import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.domain.useCase.local.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
) : BaseViewModel<HourlyUiState, HourlyUiEffect>() {

    init {
        observeIsFahrenheitSelected()
    }

    private fun observeIsFahrenheitSelected() = viewModelScope.launch {
        dataStoreUseCase.getIsFahrenheitSelected().collectLatest {
            setState(HourlyUiState.IsFahrenheitSelected(it))
        }
    }
}

sealed interface HourlyUiState : State {
    data class IsFahrenheitSelected(val isSelected: Boolean) : HourlyUiState
}

interface HourlyUiEffect : Effect