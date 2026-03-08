package com.aliyev.woweather.data.service.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreService @Inject constructor(
    private val service: DataStore<Preferences>,
) {

    private object PreferencesKey {
        val isCitySelected = booleanPreferencesKey("city_selected")
        val cityToken = stringPreferencesKey("city_token")
    }

    suspend fun setCityToken(token: String) {
        service.edit {
            it[PreferencesKey.cityToken] = token
        }
    }

    suspend fun setCitySelected(isSelected: Boolean) {
        service.edit {
            it[PreferencesKey.isCitySelected] = isSelected
        }
    }

    val cityToken: Flow<String?> = service.data.catch {
        throw it
    }.map {
        it[PreferencesKey.cityToken] ?: ""
    }

    val isCitySelected: Flow<Boolean?> = service.data.catch {
        throw it
    }.map {
        it[PreferencesKey.isCitySelected] ?: false
    }

}