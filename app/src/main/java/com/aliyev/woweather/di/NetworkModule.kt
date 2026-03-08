package com.aliyev.woweather.di

import android.content.Context
import com.aliyev.woweather.common.utils.BASE_URL
import com.aliyev.woweather.data.repository.remote.ApiRepositoryImpl
import com.aliyev.woweather.data.service.remote.ApiKeyInterceptor
import com.aliyev.woweather.data.service.remote.ApiService
import com.aliyev.woweather.data.source.remote.ApiSource
import com.aliyev.woweather.data.source.remote.ApiSourceImpl
import com.aliyev.woweather.domain.repository.remote.ApiRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun injectInterceptor() = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun injectOkHttp3(interceptor: ApiKeyInterceptor, @ApplicationContext context: Context) =
        OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            ).build()

    @Singleton
    @Provides
    fun injectRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun injectService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun injectApiSource(service: ApiService) = ApiSourceImpl(service) as ApiSource

    @Singleton
    @Provides
    fun injectApiRepository(source: ApiSource) = ApiRepositoryImpl(source) as ApiRepository


}