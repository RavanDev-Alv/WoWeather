package com.aliyev.woweather.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.aliyev.woweather.data.dto.local.RoomDB
import com.aliyev.woweather.data.repository.local.RecentSearchesRepoImpl
import com.aliyev.woweather.data.repository.local.SavedCityRepoImpl
import com.aliyev.woweather.data.service.local.RecentSearchesDao
import com.aliyev.woweather.data.service.local.SavedCityDao
import com.aliyev.woweather.data.source.local.RecentSearchesSource
import com.aliyev.woweather.data.source.local.RecentSearchesSourceImpl
import com.aliyev.woweather.data.source.local.SavedCitySource
import com.aliyev.woweather.data.source.local.SavedCitySourceImpl
import com.aliyev.woweather.domain.repository.local.RecentSearchesRepository
import com.aliyev.woweather.domain.repository.local.SavedCityRepository
import com.aliyev.woweather.domain.repository.networkObserve.ConnectivityObserver
import com.aliyev.woweather.domain.repository.networkObserve.ConnectivityObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    fun injectConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Singleton
    @Provides
    fun injectRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RoomDB::class.java, "local_room"
    ).build()

    @Singleton
    @Provides
    fun injectSavedCityDao(roomDB: RoomDB) = roomDB.getSavedCitiesDao()

    @Singleton
    @Provides
    fun injectRecentSearchesDao(roomDB: RoomDB) = roomDB.getRecentSearchesDao()

    @Provides
    @Singleton
    fun injectSource(savedCityDao: SavedCityDao) =
        SavedCitySourceImpl(savedCityDao) as SavedCitySource

    @Provides
    @Singleton
    fun injectRepo(source: SavedCitySource) = SavedCityRepoImpl(source) as SavedCityRepository


    @Provides
    @Singleton
    fun injectSourceSearch(dao: RecentSearchesDao) =
        RecentSearchesSourceImpl(dao) as RecentSearchesSource

    @Provides
    @Singleton
    fun injectRepoSearch(source: RecentSearchesSource) =
        RecentSearchesRepoImpl(source) as RecentSearchesRepository

    @Provides
    @Singleton
    fun injectConnectivityObserver(cm: ConnectivityManager) =
        ConnectivityObserverImpl(cm) as ConnectivityObserver

}