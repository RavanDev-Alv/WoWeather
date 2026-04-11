package com.aliyev.woweather.domain.useCase.local

import com.aliyev.woweather.domain.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(
    private val repo: DataStoreRepository,
) {

    suspend fun setCityToken(token: String) = repo.setCityToken(token)

    suspend fun setIsCitySelected(isSelected: Boolean) = repo.setIsCitySelected(isSelected)

    suspend fun getCityToken(): Flow<String?> = repo.getCityToken()

    suspend fun getIsCitySelected(): Flow<Boolean?> = repo.getIsCitySelected()

    fun getIsFahrenheitSelected(): Flow<Boolean> = repo.getIsFahrenheitSelected()

    suspend fun setIsFahrenheitSelected(isSelected: Boolean) =
        repo.setIsFahrenheitSelected(isSelected)

}