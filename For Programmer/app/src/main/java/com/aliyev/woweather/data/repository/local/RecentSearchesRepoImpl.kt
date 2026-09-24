package com.aliyev.woweather.data.repository.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.data.mapper.toRecentSearchesUiModel
import com.aliyev.woweather.data.source.local.RecentSearchesSource
import com.aliyev.woweather.domain.model.local.RecentSearchesUiModel
import com.aliyev.woweather.domain.repository.local.RecentSearchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecentSearchesRepoImpl @Inject constructor(
    private val source: RecentSearchesSource,
) : RecentSearchesRepository {
    override suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO) {
        source.insertRecentSearches(recentSearchesDTO)
    }

    override suspend fun getRecentSearches(): Flow<Resource<List<RecentSearchesUiModel>>> = flow {
        emit(Resource.Loading)
        when (val response = source.getRecentSearches()) {
            is Resource.Error -> emit(Resource.Error(response.throwable))
            is Resource.Success -> emit(Resource.Success(response.result?.toRecentSearchesUiModel()))
            else -> Unit
        }
    }

    override suspend fun getRecentSearchItem(id: Int): Flow<Resource<RecentSearchesUiModel>> =
        flow {
            emit(Resource.Loading)
            when (val response = source.getRecentSearchItem(id)) {
                is Resource.Error -> emit(Resource.Error(response.throwable))
                is Resource.Success -> emit(Resource.Success(response.result?.toRecentSearchesUiModel()))
                else -> Unit
            }
        }

}