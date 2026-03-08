package com.aliyev.woweather.data.dto.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>?,
)