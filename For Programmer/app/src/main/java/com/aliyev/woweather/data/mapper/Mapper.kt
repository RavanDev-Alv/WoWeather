package com.aliyev.woweather.data.mapper

import com.aliyev.woweather.data.dto.forecast.Astro
import com.aliyev.woweather.data.dto.forecast.Condition
import com.aliyev.woweather.data.dto.forecast.Current
import com.aliyev.woweather.data.dto.forecast.Day
import com.aliyev.woweather.data.dto.forecast.Forecast
import com.aliyev.woweather.data.dto.forecast.ForecastDTO
import com.aliyev.woweather.data.dto.forecast.Forecastday
import com.aliyev.woweather.data.dto.forecast.Hour
import com.aliyev.woweather.data.dto.forecast.Location
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.data.dto.search.SearchLocationDTO
import com.aliyev.woweather.domain.model.forecast.AstroUiModel
import com.aliyev.woweather.domain.model.forecast.ConditionUiModel
import com.aliyev.woweather.domain.model.forecast.CurrentUiModel
import com.aliyev.woweather.domain.model.forecast.DayUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastListUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel
import com.aliyev.woweather.domain.model.forecast.HourUiModel
import com.aliyev.woweather.domain.model.forecast.LocationUiModel
import com.aliyev.woweather.domain.model.local.RecentSearchesUiModel
import com.aliyev.woweather.domain.model.local.SavedCityUiModel
import com.aliyev.woweather.domain.model.location.SearchLocationUiModel

fun List<SearchLocationDTO>.toSearchLocationUiModel() = map {
    with(it) {
        SearchLocationUiModel(
            country, id, lat, lon, name, region, url
        )
    }
}

fun Current.toCurrentUiModel() = CurrentUiModel(
    cloud = cloud,
    condition = condition?.toConditionUiModel(),
    feelslikeC,
    feelslikeF,
    gustKph,
    gustMph,
    humidity,
    isDay,
    lastUpdated,
    lastUpdatedEpoch,
    precipIn,
    precipMm,
    pressureIn,
    pressureMb,
    tempC,
    tempF,
    uv,
    visKm,
    visMiles,
    windDegree,
    windDir,
    windKph,
    windMph
)

fun ForecastDTO.toForecastUiModel() = ForecastUiModel(
    current = current?.toCurrentUiModel(),
    forecast = forecast?.toForecastListUiModel(),
    location = location?.toLocationUiModel()
)

fun Location.toLocationUiModel() = LocationUiModel(
    country, lat, localtime, localtimeEpoch, lon, name, region, tzId
)

fun Forecast.toForecastListUiModel() = ForecastListUiModel(
    forecastday = forecastday?.toForecastdayUiModel()
)

fun List<Forecastday>.toForecastdayUiModel() = map {
    with(it) {
        ForecastdayUiModel(
            astro = astro?.toAstroUiModel(),
            date = date,
            dateEpoch = dateEpoch,
            day = day?.toDayUiModel(),
            hour = hour?.toHourUiModel(),
        )
    }
}

fun Astro.toAstroUiModel() = AstroUiModel(
    isMoonUp, isSunUp, moonIllumination, moonPhase, moonrise, moonset, sunrise, sunset
)

fun Condition.toConditionUiModel() = ConditionUiModel(
    code, icon, text
)

fun Day.toDayUiModel() = DayUiModel(
    condition = condition?.toConditionUiModel(),
    avghumidity = avghumidity,
    avgtempC = avgtempC,
    avgtempF = avgtempF,
    avgvisKm = avgvisKm,
    avgvisMiles = avgvisMiles,
    dailyChanceOfRain = dailyChanceOfRain,
    dailyChanceOfSnow = dailyChanceOfSnow,
    dailyWillItRain = dailyWillItRain,
    dailyWillItSnow = dailyWillItSnow,
    maxtempC = maxtempC,
    maxtempF = maxtempF,
    maxwindKph = maxwindKph,
    maxwindMph = maxwindMph,
    mintempC = mintempC,
    mintempF = mintempF,
    totalprecipIn = totalprecipIn,
    totalprecipMm = totalprecipMm,
    totalsnowCm = totalsnowCm,
    uv = uv
)

fun List<Hour>.toHourUiModel() = map {
    with(it) {
        HourUiModel(
            chanceOfRain,
            chanceOfSnow,
            cloud,
            condition,
            dewpointC,
            dewpointF,
            feelslikeC,
            feelslikeF,
            gustKph,
            gustMph,
            heatindexC,
            heatindexF,
            humidity,
            isDay,
            precipIn,
            precipMm,
            pressureIn,
            pressureMb,
            tempC,
            tempF,
            time,
            timeEpoch,
            uv,
            visKm,
            visMiles,
            willItRain,
            willItSnow,
            windDegree,
            windDir,
            windKph,
            windMph,
            windchillC,
            windchillF
        )
    }
}

fun List<SavedCityDTO>.toSavedCityUiModel() = map {
    with(it) {
        SavedCityUiModel(
            id, name, region, country, token
        )
    }
}

fun RecentSearchesDTO.toRecentSearchesUiModel() = RecentSearchesUiModel(
    id, name, region, country
)

fun List<RecentSearchesDTO>.toRecentSearchesUiModel() = map {
    with(it) {
        RecentSearchesUiModel(
            id, name, region, country
        )
    }
}

fun SavedCityDTO.toSavedCityUiModel() = SavedCityUiModel(id, name, region, country, token)

