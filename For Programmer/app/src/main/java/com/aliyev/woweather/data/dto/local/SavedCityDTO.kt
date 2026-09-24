package com.aliyev.woweather.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedCityDTO")
data class SavedCityDTO(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String?,
    val region: String?,
    val country: String?,
    val token: String?,
)
