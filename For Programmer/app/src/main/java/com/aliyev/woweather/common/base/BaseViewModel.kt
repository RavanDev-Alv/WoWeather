package com.aliyev.woweather.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliyev.woweather.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EFFECT : Effect> : ViewModel() {

    private val _liveData: MutableLiveData<STATE> = MutableLiveData<STATE>()
    val liveData: LiveData<STATE> get() = _liveData

    private val _effect: MutableLiveData<EFFECT> = MutableLiveData<EFFECT>()
    val effect: LiveData<EFFECT> get() = _effect


    fun setState(state: STATE) {
        viewModelScope.launch {
            _liveData.value = state
        }
    }

    fun setEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.value = effect
        }
    }

    inline fun <T> Flow<Resource<T>>.handleResult(
        crossinline onComplete: (T) -> Unit,
        crossinline onError: (Throwable) -> Unit,
        crossinline onLoading: () -> Unit,
    ) {
        onEach { response ->
            when (response) {
                is Resource.Error -> {
                    onError(response.throwable)
                }

                is Resource.Success -> {
                    response.result?.let { onComplete(it) }
                }

                is Resource.Loading -> {
                    onLoading()
                }
            }
        }.launchIn(viewModelScope)
    }
}

interface State
interface Effect