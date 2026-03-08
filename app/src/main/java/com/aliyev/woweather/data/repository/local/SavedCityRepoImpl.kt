package com.aliyev.woweather.data.repository.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.SavedCityDTO
import com.aliyev.woweather.data.mapper.toSavedCityUiModel
import com.aliyev.woweather.data.source.local.SavedCitySource
import com.aliyev.woweather.domain.model.local.SavedCityUiModel
import com.aliyev.woweather.domain.repository.local.SavedCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedCityRepoImpl @Inject constructor(
    private val source: SavedCitySource,
) : SavedCityRepository {
    override suspend fun insertSavedCity(savedCityDTO: SavedCityDTO) {
        source.insertSavedCity(savedCityDTO)
    }

    override suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO) {
        source.deleteSavedCity(savedCityDTO)
    }

    override suspend fun getAllSavedCities(): Flow<Resource<List<SavedCityUiModel>>> = flow {
        emit(Resource.Loading)
        when (val response = source.getAllSavedCities()) {
            is Resource.Error -> emit(Resource.Error(response.throwable))
            is Resource.Success -> emit(Resource.Success(response.result?.toSavedCityUiModel()))
            else -> Unit
        }
    }

    override suspend fun getSavedCity(id: Int): Flow<Resource<SavedCityUiModel>> = flow {
        emit(Resource.Loading)
        when (val response = source.getSavedCity(id)) {
            is Resource.Error -> emit(Resource.Error(response.throwable))
            is Resource.Success -> emit(Resource.Success(response.result?.toSavedCityUiModel()))
            else -> Unit
        }
    }
}