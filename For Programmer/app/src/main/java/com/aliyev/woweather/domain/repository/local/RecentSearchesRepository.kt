package com.aliyev.woweather.domain.repository.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.domain.model.local.RecentSearchesUiModel
import kotlinx.coroutines.flow.Flow

interface RecentSearchesRepository {
    suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO)

    suspend fun getRecentSearches(): Flow<Resource<List<RecentSearchesUiModel>>>

    suspend fun getRecentSearchItem(id: Int): Flow<Resource<RecentSearchesUiModel>>
}