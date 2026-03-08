package com.aliyev.woweather.data.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliyev.woweather.data.dto.local.SavedCityDTO

@Dao
interface SavedCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedCity(savedCityDTO: SavedCityDTO)

    @Delete
    suspend fun deleteSavedCity(savedCityDTO: SavedCityDTO)

    @Query("select * from savedcitydto")
    suspend fun getAllSavedCities(): List<SavedCityDTO>

    @Query("select * from savedcitydto where id=:id limit 1")
    suspend fun getSavedCity(id: Int): SavedCityDTO


}