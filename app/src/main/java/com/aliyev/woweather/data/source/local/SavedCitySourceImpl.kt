package com.aliyev.woweather.data.source.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.data.service.local.SavedCityDao
import javax.inject.Inject

class SavedCitySourceImpl @Inject constructor(
    private val service: SavedCityDao,
) : SavedCitySource {
    override suspend fun insertSavedCity(savedCityDTO: SavedCityDTO) {
        service.insertSavedCity(savedCityDTO)
    }

    override suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO) {
        service.deleteSavedCity(savedCityDTO)
    }

    override suspend fun getAllSavedCities(): Resource<List<SavedCityDTO>> =
        handleResult { service.getAllSavedCities() }

    override suspend fun getSavedCity(id: Int): Resource<SavedCityDTO> =
        handleResult { service.getSavedCity(id) }

    private suspend fun <T> handleResult(response: suspend () -> T): Resource<T> {
        return try {
            val data = response.invoke()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


}