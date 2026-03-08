package com.aliyev.woweather.presentation.ui.hourly


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.getTime
import com.aliyev.woweather.databinding.FragmentHourlyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HourlyFragment :
    BaseFragment<FragmentHourlyBinding, HourlyViewModel>(FragmentHourlyBinding::inflate) {
    override val viewModel: HourlyViewModel by viewModels()
    private val args by navArgs<HourlyFragmentArgs>()
    private val hourData by lazy { args.hourData }

    override fun observeEvents() {

    }


    override fun onViewCreateFinish() {
        setup()
    }

    private fun setup() {
        with(binding) {
            data = hourData
            isDay = hourData.isDay == 1
            localTime = getTime()

            buttonBack.setOnClickListener { findNavController().popBackStack() }
        }
    }


}