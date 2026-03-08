package com.aliyev.woweather.data.source.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.data.service.local.RecentSearchesDao
import javax.inject.Inject

class RecentSearchesSourceImpl @Inject constructor(
    private val service: RecentSearchesDao,
) : RecentSearchesSource {
    override suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO) {
        service.insertRecentSearches(recentSearchesDTO)
    }

    override suspend fun getRecentSearches(): Resource<List<RecentSearchesDTO>> =
        handleResponse { service.getRecentSearches() }

    override suspend fun getRecentSearchItem(id: Int): Resource<RecentSearchesDTO> =
        handleResponse { service.getRecentSearchedCity(id) }

    private suspend fun <T> handleResponse(response: suspend () -> T): Resource<T> {
        return try {
            val data = response.invoke()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


}