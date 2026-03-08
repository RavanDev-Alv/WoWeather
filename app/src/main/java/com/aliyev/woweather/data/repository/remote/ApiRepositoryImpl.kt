package com.aliyev.woweather.data.repository.remote

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.mapper.toForecastUiModel
import com.aliyev.woweather.data.mapper.toSearchLocationUiModel
import com.aliyev.woweather.data.source.remote.ApiSource
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.model.location.SearchLocationUiModel
import com.aliyev.woweather.domain.repository.remote.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val source: ApiSource,
) : ApiRepository {

    override suspend fun searchCity(city: String): Flow<Resource<List<SearchLocationUiModel>>> =
        flow {
            emit(Resource.Loading)
            when (val response = source.searchCity(city)) {
                is Resource.Error -> emit(Resource.Error(response.throwable))
                is Resource.Success -> emit(Resource.Success(response.result?.toSearchLocationUiModel()))
                else -> Unit
            }
        }

    override suspend fun getForecast(city: String, days: Int): Flow<Resource<ForecastUiModel>> =
        flow {
            emit(Resource.Loading)
            when (val response = source.getForecast(city, days)) {
                is Resource.Error -> emit(Resource.Error(response.throwable))
                is Resource.Success -> emit(Resource.Success(response.result?.toForecastUiModel()))
                else -> Unit
            }
        }

}