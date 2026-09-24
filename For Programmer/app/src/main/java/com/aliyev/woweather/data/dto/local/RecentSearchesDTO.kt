package com.aliyev.woweather.data.dto.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecentSearches")
data class RecentSearchesDTO(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String?,
    val region: String?,
    val country: String?,
)