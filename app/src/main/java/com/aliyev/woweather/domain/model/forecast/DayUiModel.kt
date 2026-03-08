package com.aliyev.woweather.domain.model.forecast

data class DayUiModel(
    val avghumidity: Double?,
    val avgtempC: Double?,
    val avgtempF: Double?,
    val avgvisKm: Double?,
    val avgvisMiles: Double?,
    val condition: ConditionUiModel?,
    val dailyChanceOfRain: Int?,
    val dailyChanceOfSnow: Int?,
    val dailyWillItRain: Int?,
    val dailyWillItSnow: Int?,
    val maxtempC: Double?,
    val maxtempF: Double?,
    val maxwindKph: Double?,
    val maxwindMph: Double?,
    val mintempC: Double?,
    val mintempF: Double?,
    val totalprecipIn: Double?,
    val totalprecipMm: Double?,
    val totalsnowCm: Double?,
    val uv: Double?,
)
