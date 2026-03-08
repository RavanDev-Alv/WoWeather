package com.aliyev.woweather.domain.model.forecast

data class ForecastdayUiModel(
    val astro: AstroUiModel?,
    val date: String?,
    val dateEpoch: Int?,
    val day: DayUiModel?,
    val hour: List<HourUiModel>?,
)
