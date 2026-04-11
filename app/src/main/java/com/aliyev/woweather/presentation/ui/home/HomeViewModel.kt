package com.aliyev.woweather.presentation.ui.home


import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.useCase.local.DataStoreUseCase
import com.aliyev.woweather.domain.useCase.remote.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
) : BaseViewModel<HomeUiState, HomeUiEffect>() {

    init {
        getCityToken()
    }


    fun getCityToken() {
        viewModelScope.launch {
            dataStoreUseCase.getCityToken().collectLatest {
                setState(HomeUiState.CityToken(it ?: ""))
            }
        }
    }

    fun getForecast(city: String, days: Int) {
        viewModelScope.launch {
            val isFahrenheitSelected =
                dataStoreUseCase.getIsFahrenheitSelected().firstOrNull() == true

            locationUseCase.getForecast(city, days).handleResult(
                onComplete = {
                    setState(HomeUiState.Forecast(it, isFahrenheitSelected))
                },
                onLoading = {
                    setState(HomeUiState.Loading)
                },
                onError = {
                    setState(HomeUiState.Error)
                    setEffect(HomeUiEffect.ShowMessage(it.localizedMessage as String))
                }
            )
        }
    }

}

sealed interface HomeUiState : State {

    data object Error : HomeUiState

    data object Loading : HomeUiState

    data class CityToken(val token: String) : HomeUiState

    data class Forecast(val data: ForecastUiModel, val isFahrenheitSelected: Boolean) : HomeUiState

}

sealed interface HomeUiEffect : Effect {

    data class ShowMessage(val message: String) : HomeUiEffect

}