package com.aliyev.woweather.domain.useCase.local

import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.domain.repository.local.SavedCityRepository
import javax.inject.Inject

class SavedCityUseCase @Inject constructor(
    private val repo: SavedCityRepository,
) {

    suspend fun insertSavedCity(savedCityDTO: SavedCityDTO) = repo.insertSavedCity(savedCityDTO)

    suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO) = repo.deleteSavedCity(savedCityDTO)

    suspend fun getAllSavedCities() = repo.getAllSavedCities()

    suspend fun getSavedCity(id: Int) = repo.getSavedCity(id)

}