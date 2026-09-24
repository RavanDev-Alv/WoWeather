package com.aliyev.woweather.domain.model.forecast

data class ForecastUiModel(
    val current: CurrentUiModel?,
    val forecast: ForecastListUiModel?,
    val location: LocationUiModel?,
)
