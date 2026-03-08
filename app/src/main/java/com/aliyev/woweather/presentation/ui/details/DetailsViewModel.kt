package com.aliyev.woweather.presentation.ui.details

import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.useCase.remote.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase,
) : BaseViewModel<DetailsUiState, DetailsUiEffect>() {

    fun getForecast(city: String, days: Int) {
        viewModelScope.launch {
            locationUseCase.getForecast(city = city, days = days).handleResult(
                onComplete = {
                    setState(DetailsUiState.ForecastData(it))
                },
                onError = {
                    setState(DetailsUiState.Error)
                    setEffect(DetailsUiEffect.ShowMessage(it.localizedMessage as String))
                },
                onLoading = {
                    setState(DetailsUiState.Loading)
                }
            )
        }
    }
}

interface DetailsUiState : State {

    data object Loading : DetailsUiState

    data object Error : DetailsUiState

    data class ForecastData(val data: ForecastUiModel) : DetailsUiState

}

interface DetailsUiEffect : Effect {

    data class ShowMessage(val message: String) : DetailsUiEffect

}