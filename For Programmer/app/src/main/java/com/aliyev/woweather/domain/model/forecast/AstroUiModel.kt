package com.aliyev.woweather.domain.model.forecast


data class AstroUiModel(
    val isMoonUp: Int?,
    val isSunUp: Int?,
    val moonIllumination: Int?,
    val moonPhase: String?,
    val moonrise: String?,
    val moonset: String?,
    val sunrise: String?,
    val sunset: String?,
)
