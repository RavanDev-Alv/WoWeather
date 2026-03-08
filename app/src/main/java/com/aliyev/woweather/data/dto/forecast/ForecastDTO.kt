package com.aliyev.woweather.data.dto.forecast


import com.google.gson.annotations.SerializedName

data class ForecastDTO(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("forecast")
    val forecast: Forecast?,
    @SerializedName("location")
    val location: Location?,
)