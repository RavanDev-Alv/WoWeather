package com.aliyev.woweather.data.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aliyev.woweather.data.dto.local.RecentSearchesDTO

@Dao
interface RecentSearchesDao {

    @Insert
    suspend fun insertRecentSearches(recentSearchesDTO: RecentSearchesDTO)

    @Query("select * from recentsearches")
    suspend fun getRecentSearches(): List<RecentSearchesDTO>

    @Query("select * from recentsearches where id=:id limit 1")
    suspend fun getRecentSearchedCity(id: Int): RecentSearchesDTO

}