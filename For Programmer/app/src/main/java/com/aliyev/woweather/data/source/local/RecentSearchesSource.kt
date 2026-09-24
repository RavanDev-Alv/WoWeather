package com.aliyev.woweather.data.source.local

import com.aliyev.woweather.common.Resource
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO

interface RecentSearchesSource {

    suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO)

    suspend fun getRecentSearches(): Resource<List<RecentSearchesDTO>>

    suspend fun getRecentSearchItem(id: Int): Resource<RecentSearchesDTO>

}