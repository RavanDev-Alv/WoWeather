package com.aliyev.woweather.domain.useCase.remote

import com.aliyev.woweather.domain.repository.remote.ApiRepository
import javax.inject.Inject

class LocationUseCase @Inject constructor(private val repo: ApiRepository) {

    suspend fun searchCity(city: String) = repo.searchCity(city)

    suspend fun getForecast(city: String, days: Int) = repo.getForecast(city, days)

}