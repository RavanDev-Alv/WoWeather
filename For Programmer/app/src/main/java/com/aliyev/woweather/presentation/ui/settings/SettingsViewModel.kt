package com.aliyev.woweather.presentation.ui.settings

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
class SettingsViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<SettingsUiState, SettingsUiEffect>() {

    init {
        viewModelScope.launch {
            dataStoreUseCase.getIsFahrenheitSelected().collectLatest {
                setState(SettingsUiState.IsFahrenheitSelected(it))
            }
        }
    }

    fun onClickSwithTemperature(isChecked: Boolean) = viewModelScope.launch {
        dataStoreUseCase.setIsFahrenheitSelected(isChecked)
    }

    fun navigateBack() = setEffect(SettingsUiEffect.NavigateBack)

}

sealed interface SettingsUiState : State {

    data class IsFahrenheitSelected(val data: Boolean) : SettingsUiState

}

sealed interface SettingsUiEffect : Effect {

    data object NavigateBack : SettingsUiEffect

}