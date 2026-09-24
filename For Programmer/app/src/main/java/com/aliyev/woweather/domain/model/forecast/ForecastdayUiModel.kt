package com.aliyev.woweather.domain.model.forecast

data class ForecastdayUiModel(
    val astro: AstroUiModel?,
    val date: String?,
    val dateEpoch: Int?,
    val day: DayUiModel?,
    val hour: List<HourUiModel>?,
) {

    companion object {
        fun ForecastdayUiModel?.getTemperature(isFahrenheitSelected: Boolean) =
            if (isFahrenheitSelected) "${this?.day?.avgtempF} ºF" else "${this?.day?.avgtempC} ºC"

    }

}
