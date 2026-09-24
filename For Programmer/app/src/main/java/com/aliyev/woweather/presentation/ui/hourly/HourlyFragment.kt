package com.aliyev.woweather.presentation.ui.hourly


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.getTime
import com.aliyev.woweather.databinding.FragmentHourlyBinding
import com.aliyev.woweather.domain.model.forecast.HourUiModel.Companion.getTemperature
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HourlyFragment :
    BaseFragment<FragmentHourlyBinding, HourlyViewModel>(FragmentHourlyBinding::inflate) {
    override val viewModel: HourlyViewModel by viewModels()
    private val args by navArgs<HourlyFragmentArgs>()
    private val hourData by lazy { args.hourData }

    private var isFahrenheitSelected = false

    override fun observeEvents() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is HourlyUiState.IsFahrenheitSelected -> {
                    isFahrenheitSelected = it.isSelected
                    binding.textView27.text = hourData.getTemperature(isFahrenheitSelected)
                }
            }
        }
    }


    override fun onViewCreateFinish() {
        setup()
    }

    private fun setup() {
        (viewModel.liveData.value as? HourlyUiState.IsFahrenheitSelected)?.let {
            isFahrenheitSelected = it.isSelected
        }
        with(binding) {
            data = hourData
            isDay = hourData.isDay == 1
            localTime = getTime()
            textView27.text = hourData.getTemperature(isFahrenheitSelected)

            buttonBack.setOnClickListener { findNavController().popBackStack() }
        }
    }


}