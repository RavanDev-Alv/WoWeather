package com.aliyev.woweather.data.service.remote

import com.aliyev.woweather.data.dto.forecast.ForecastDTO
import com.aliyev.woweather.data.dto.search.SearchLocationDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.json")
    suspend fun searchCity(@Query("q") city: String): Response<List<SearchLocationDTO>>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("days") days: Int,
    ): Response<ForecastDTO>

}