package com.aliyev.woweather.domain.repository.local

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setCityToken(token: String)

    suspend fun setIsCitySelected(isSelected: Boolean)

    suspend fun getCityToken(): Flow<String?>

    suspend fun getIsCitySelected(): Flow<Boolean?>

}