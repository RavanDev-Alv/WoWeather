package com.aliyev.woweather.presentation.ui.location

import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.domain.model.local.RecentSearchesUiModel
import com.aliyev.woweather.domain.model.local.SavedCityUiModel
import com.aliyev.woweather.domain.model.location.SearchLocationUiModel
import com.aliyev.woweather.domain.useCase.local.DataStoreUseCase
import com.aliyev.woweather.domain.useCase.local.RecentSearchesUseCase
import com.aliyev.woweather.domain.useCase.local.SavedCityUseCase
import com.aliyev.woweather.domain.useCase.remote.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val savedCityUseCase: SavedCityUseCase,
    private val recentSearchesUseCase: RecentSearchesUseCase,
) : BaseViewModel<LocationUiState, LocationUiEffect>() {

    init {
        isCitySelected()
    }

    fun searchCity(city: String) {
        viewModelScope.launch {
            locationUseCase.searchCity(city).handleResult(
                onComplete = {
                    setState(LocationUiState.SearchCity(it))
                },
                onLoading = {
                    setState(LocationUiState.Loading)
                },
                onError = {
                    setState(LocationUiState.Error)
                    setEffect(LocationUiEffect.ShowMessage(it.localizedMessage as String))
                }
            )
        }
    }

    fun setCityToken(token: String) {
        viewModelScope.launch {
            dataStoreUseCase.setCityToken(token)
        }
    }

    fun setIsCitySelected(isSelected: Boolean) {
        viewModelScope.launch {
            dataStoreUseCase.setIsCitySelected(isSelected)
        }
    }

    fun isCitySelected() {
        viewModelScope.launch {
            dataStoreUseCase.getIsCitySelected().collectLatest {
                setState(LocationUiState.IsCitySelected(it ?: false))
            }
        }
    }

    fun insertSavedCity(savedCityDTO: SavedCityDTO) {
        viewModelScope.launch {
            savedCityUseCase.insertSavedCity(savedCityDTO)
        }
    }

    fun getSavedCities() {
        viewModelScope.launch {
            savedCityUseCase.getAllSavedCities().handleResult(
                onComplete = {
                    setState(LocationUiState.SavedCities(it))
                },
                onLoading = {
                    setState(LocationUiState.Loading)
                },
                onError = {
                    setState(LocationUiState.Error)
                    setEffect(LocationUiEffect.ShowMessage(it.localizedMessage as String))
                }
            )
        }


    }

    fun deleteSavedCity(savedCityDTO: SavedCityDTO) {
        viewModelScope.launch {
            savedCityUseCase.deleteSavedCity(savedCityDTO)
            setState(LocationUiState.DeleteComplete)
        }
    }

    fun getRecentSearch() {
        viewModelScope.launch {
            recentSearchesUseCase.getRecentSearches().handleResult(
                onComplete = {
                    setState(LocationUiState.RecentSearches(it))
                },
                onLoading = {
                    setState(LocationUiState.Loading)
                },
                onError = {
                    setState(LocationUiState.Error)
                    setEffect(LocationUiEffect.ShowMessage(it.localizedMessage as String))
                }
            )
        }
    }

    fun insertRecentSearch(recentSearchesDTO: RecentSearchesDTO) {
        viewModelScope.launch {
            recentSearchesUseCase.insertRecentSearches(recentSearchesDTO)
        }
    }

}

sealed interface LocationUiState : State {

    data object Error : LocationUiState

    data object Loading : LocationUiState

    data object DeleteComplete : LocationUiState

    data class SearchCity(val result: List<SearchLocationUiModel>) : LocationUiState

    data class IsCitySelected(val isSelected: Boolean) : LocationUiState

    data class SavedCities(val data: List<SavedCityUiModel>) : LocationUiState

    data class RecentSearches(val data: List<RecentSearchesUiModel>) : LocationUiState

}

sealed interface LocationUiEffect : Effect {

    data class ShowMessage(val message: String? = null) : LocationUiEffect

}