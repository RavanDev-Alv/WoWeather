package com.aliyev.woweather.domain.useCase.local

import com.aliyev.woweather.data.dto.local.RecentSearchesDTO
import com.aliyev.woweather.domain.repository.local.RecentSearchesRepository
import javax.inject.Inject

class RecentSearchesUseCase @Inject constructor(private val repo: RecentSearchesRepository) {

    suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO) =
        repo.insertRecentSearches(recentSearchesDTO)

    suspend fun getRecentSearches() = repo.getRecentSearches()

    suspend fun getRecentSearchItem(id: Int) = repo.getRecentSearchItem(id)

}