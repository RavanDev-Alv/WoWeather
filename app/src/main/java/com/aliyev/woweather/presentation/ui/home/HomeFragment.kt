package com.aliyev.woweather.presentation.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.aliyev.woweather.R
import com.aliyev.woweather.common.base.BaseFragment
import com.aliyev.woweather.common.utils.DAYS
import com.aliyev.woweather.common.utils.WeatherType
import com.aliyev.woweather.common.utils.enableTransitionAnimation
import com.aliyev.woweather.common.utils.progressDialog
import com.aliyev.woweather.common.utils.setStatusBarColor
import com.aliyev.woweather.databinding.FragmentHomeBinding
import com.aliyev.woweather.domain.model.forecast.CurrentUiModel.Companion.getTemperature
import com.aliyev.woweather.domain.model.forecast.ForecastUiModel
import com.aliyev.woweather.presentation.ui.home.adapters.DailyAdapter
import com.aliyev.woweather.presentation.ui.home.adapters.HourlyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private val hourlyAdapter = HourlyAdapter()
    private val dailyAdapter = DailyAdapter()
    private lateinit var _token: String

    override fun observeEvents() {
        with(viewModel) {
            val pb = progressDialog(requireContext())
            liveData.observe(viewLifecycleOwner) {
                when (it) {
                    is HomeUiState.CityToken -> {
                        _token = it.token
                        viewModel.getForecast(it.token, DAYS)
                    }

                    HomeUiState.Error -> pb.cancel()
                    is HomeUiState.Forecast -> {
                        pb.cancel()
                        setUI(it.data, it.isFahrenheitSelected)
                    }

                    is HomeUiState.Loading -> pb.show()
                }
            }
        }
    }

    override fun onViewCreateFinish() {
        enableTransitionAnimation()
        setup()
    }

    private fun setup() {
        enableTransitionAnimation()
        setRV()
        with(binding) {
            cardToday.setOnClickListener {

                val extras = FragmentNavigatorExtras(
                    cardToday to cardToday.transitionName,
                    constraintCard to constraintCard.transitionName
                )
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        _token,
                        WeatherType.TODAY,
                        ""
                    ),
                    extras
                )

            }
            buttonRefresh.setOnClickListener {
                viewModel.getForecast(_token, DAYS)
            }
            buttonSearch.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                )
            }
            buttonLocation.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLocationManagerFragment()) }

            buttonSettings.setOnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment()) }
        }
    }

    private fun setUI(forecast: ForecastUiModel, isFahrenheitSelected: Boolean) {
        with(binding) {
            data = forecast
            isDay = forecast.current?.isDay == 1

            setStatusBarColor(if (forecast.current?.isDay == 1) R.color.blue_4F else R.color.black_4C)

            textCurrentTemperature.text = forecast.current.getTemperature(isFahrenheitSelected)

            hourlyAdapter.isFahrenheitSelected = isFahrenheitSelected
            dailyAdapter.isFahrenheitSelected = isFahrenheitSelected
            hourlyAdapter.submitData(
                forecast.forecast?.forecastday?.firstOrNull()?.hour?.filter { it.time.toString() >= forecast.current?.lastUpdated.toString() },
                forceUpdate = true
            )
            dailyAdapter.submitData(forecast.forecast?.forecastday, forceUpdate = true)
        }
    }

    private fun setRV() {
        with(binding) {
            rvHourly.adapter = hourlyAdapter
            rvDaily.adapter = dailyAdapter

            dailyAdapter.onClickDailyItem = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        _token,
                        WeatherType.DAILY,
                        it.date
                    )
                )
            }
            hourlyAdapter.onClickHourlyItem = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToHourlyFragment(
                        it
                    )
                )
            }
        }
    }


}