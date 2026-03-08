package com.aliyev.woweather.presentation.ui.hourly

import com.aliyev.woweather.common.base.BaseViewModel
import com.aliyev.woweather.common.base.Effect
import com.aliyev.woweather.common.base.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HourlyViewModel @Inject constructor() : BaseViewModel<HourlyUiState, HourlyUiEffect>()

interface HourlyUiState : State

interface HourlyUiEffect : Effect