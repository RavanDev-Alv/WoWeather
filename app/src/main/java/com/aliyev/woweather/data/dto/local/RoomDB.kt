package com.aliyev.woweather.data.dto.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aliyev.woweather.data.service.local.RecentSearchesDao
import com.aliyev.woweather.data.service.local.SavedCityDao

@Database(entities = [SavedCityDTO::class, RecentSearchesDTO::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getSavedCitiesDao(): SavedCityDao

    abstract fun getRecentSearchesDao(): RecentSearchesDao

}