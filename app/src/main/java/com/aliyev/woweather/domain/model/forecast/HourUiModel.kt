package com.aliyev.woweather.domain.model.forecast

import android.os.Parcelable
import com.aliyev.woweather.data.dto.forecast.Condition
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourUiModel(
    val chanceOfRain: Int?,
    val chanceOfSnow: Int?,
    val cloud: Int?,
    val condition: Condition?,
    val dewpointC: Double?,
    val dewpointF: Double?,
    val feelslikeC: Double?,
    val feelslikeF: Double?,
    val gustKph: Double?,
    val gustMph: Double?,
    val heatindexC: Double?,
    val heatindexF: Double?,
    val humidity: Int?,
    val isDay: Int?,
    val precipIn: Double?,
    val precipMm: Double?,
    val pressureIn: Double?,
    val pressureMb: Double?,
    val tempC: Double?,
    val tempF: Double?,
    val time: String?,
    val timeEpoch: Int?,
    val uv: Double?,
    val visKm: Double?,
    val visMiles: Double?,
    val willItRain: Int?,
    val willItSnow: Int?,
    val windDegree: Int?,
    val windDir: String?,
    val windKph: Double?,
    val windMph: Double?,
    val windchillC: Double?,
    val windchillF: Double?,
) : Parcelable
