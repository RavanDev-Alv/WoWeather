package com.aliyev.woweather.data.source.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.SavedCityDTO

interface SavedCitySource {

    suspend fun insertSavedCity(savedCityDTO: SavedCityDTO)

    suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO)

    suspend fun getAllSavedCities(): Resource<List<SavedCityDTO>>

    suspend fun getSavedCity(id: Int): Resource<SavedCityDTO>

}