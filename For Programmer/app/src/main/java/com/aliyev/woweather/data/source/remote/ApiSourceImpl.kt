package com.aliyev.woweather.data.source.remote

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.common.utils.findExceptionMessage
import com.aliyev.woweather.data.dto.forecast.ForecastDTO
import com.aliyev.woweather.data.dto.search.SearchLocationDTO
import com.aliyev.woweather.data.service.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class ApiSourceImpl @Inject constructor(
    private val service: ApiService,
) : ApiSource {

    override suspend fun searchCity(city: String): Resource<List<SearchLocationDTO>> =
        handleResponse { service.searchCity(city) }

    override suspend fun getForecast(city: String, days: Int): Resource<ForecastDTO> =
        handleResponse { service.getForecast(city, days) }


    private suspend fun <T> handleResponse(response: suspend () -> Response<T>): Resource<T> {
        return try {
            val apiResponse = response.invoke()
            if (apiResponse.isSuccessful) {
                apiResponse.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(Exception("Error"))
            } else {
                Resource.Error(Exception(findExceptionMessage(apiResponse.errorBody())))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


}