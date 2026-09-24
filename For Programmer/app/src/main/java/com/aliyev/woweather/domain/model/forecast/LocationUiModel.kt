package com.aliyev.woweather.domain.model.forecast

data class LocationUiModel(
    val country: String?,
    val lat: Double?,
    val localtime: String?,
    val localtimeEpoch: Int?,
    val lon: Double?,
    val name: String?,
    val region: String?,
    val tzId: String?,
)
