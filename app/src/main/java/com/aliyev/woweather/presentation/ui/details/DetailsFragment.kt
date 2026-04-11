package com.aliyev.woweather.presentation.ui.details


import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.DAYS
import com.aliyev.woweather.common.utils.WeatherType
import com.aliyev.woweather.common.utils.animateNumbers
import com.aliyev.woweather.common.utils.enableTransitionAnimation
import com.aliyev.woweather.common.utils.progressDialog
import com.aliyev.woweather.databinding.FragmentDetailsBinding
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel.Companion.getTemperature
import com.aliyev.woweather.presentation.ui.home.adapters.HourlyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class DetailsFragment :
    BaseFragment<FragmentDetailsBinding, DetailsViewModel>(FragmentDetailsBinding::inflate) {
    override val viewModel: DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()
    private val _token by lazy {
        args.city
    }
    private val _type by lazy { args.type }
    private val _date by lazy { args.date }
    private val hourlyAdapter = HourlyAdapter()

    override fun observeEvents() {
        with(viewModel) {
            val pb = progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner) {
                when (it) {
                    is DetailsUiState.Loading -> {
                        pb.show()
                    }

                    is DetailsUiState.Error -> {
                        pb.cancel()
                    }

                    is DetailsUiState.ForecastData -> {
                        pb.cancel()
                        setData(it.data, it.isFahrenheitSelected)
                    }
                }
            }
            effect.observe(viewLifecycleOwner) {
                when (it) {
                    is DetailsUiEffect.ShowMessage -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onViewCreateFinish() {
        enableTransitionAnimation()
        setup()
    }

    private fun setup() {
        setRV()
        viewModel.getForecast(_token, 7)
        with(binding) {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonRefresh.setOnClickListener {
                viewModel.getForecast(_token, DAYS)
            }
        }
    }

    private fun setRV() {
        with(binding) {
            rvHourly.adapter = hourlyAdapter

            hourlyAdapter.onClickHourlyItem = {
                findNavController().navigate(
                    DetailsFragmentDirections.actionDetailsFragmentToHourlyFragment(
                        it
                    )
                )
            }

        }
    }

    private fun setData(data: ForecastUiModel, isFahrenheitSelected: Boolean) {
        with(binding) {
            forecastData = data
            isDay = data.current?.isDay == 1

            val forecastDayItem: ForecastdayUiModel? = when (_type) {
                WeatherType.TODAY -> data.forecast?.forecastday?.firstOrNull()
                WeatherType.DAILY -> data.forecast?.forecastday?.firstOrNull { it.date == _date }
            }

            forecastDay = forecastDayItem

            hourlyAdapter.isFahrenheitSelected = isFahrenheitSelected
            hourlyAdapter.submitData(forecastDayItem?.hour)
            textView11.text = forecastDayItem.getTemperature(isFahrenheitSelected)
            val randomAqi = Random.nextInt(0, 100)
            textAqi.animateNumbers(0, randomAqi)
            aqiView.animateProgress(randomAqi.toFloat())
        }
    }


}