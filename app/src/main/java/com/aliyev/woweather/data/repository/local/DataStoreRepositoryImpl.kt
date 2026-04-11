package com.aliyev.woweather.data.repository.local

import com.aliyev.woweather.data.service.local.DataStoreService
import com.aliyev.woweather.domain.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val service: DataStoreService,
) : DataStoreRepository {

    override suspend fun setCityToken(token: String) {
        service.setCityToken(token)
    }

    override suspend fun setIsCitySelected(isSelected: Boolean) {
        service.setCitySelected(isSelected)
    }


    override suspend fun setIsFahrenheitSelected(isSelected: Boolean) {
        service.setIsFahrenheitSelected(isSelected)
    }

    override suspend fun getCityToken(): Flow<String?> = service.cityToken

    override suspend fun getIsCitySelected(): Flow<Boolean?> = service.isCitySelected

    override fun getIsFahrenheitSelected(): Flow<Boolean> = service.isFahrenheitSelected
}