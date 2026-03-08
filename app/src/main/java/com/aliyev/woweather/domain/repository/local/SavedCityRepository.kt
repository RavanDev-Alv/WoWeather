package com.aliyev.woweather.domain.repository.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.domain.model.local.SavedCityUiModel
import kotlinx.coroutines.flow.Flow

interface SavedCityRepository {

    suspend fun insertSavedCity(savedCityDTO: SavedCityDTO)

    suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO)

    suspend fun getAllSavedCities(): Flow<Resource<List<SavedCityUiModel>>>

    suspend fun getSavedCity(id: Int): Flow<Resource<SavedCityUiModel>>

}