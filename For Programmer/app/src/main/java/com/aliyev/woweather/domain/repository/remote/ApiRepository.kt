package com.aliyev.woweather.domain.repository.remote

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.model.location.SearchLocationUiModel
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun searchCity(city: String): Flow<Resource<List<SearchLocationUiModel>>>

    suspend fun getForecast(city: String, days: Int): Flow<Resource<ForecastUiModel>>

}