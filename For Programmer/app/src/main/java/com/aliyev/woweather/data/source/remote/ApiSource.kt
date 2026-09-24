package com.aliyev.woweather.data.source.remote

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.forecast.ForecastDTO
import com.aliyev.woweather.data.dto.search.SearchLocationDTO

interface ApiSource {

    suspend fun searchCity(city: String): Resource<List<SearchLocationDTO>>

    suspend fun getForecast(city: String, days: Int): Resource<ForecastDTO>

}