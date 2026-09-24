package com.aliyev.woweather.common


sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val result: T?) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}