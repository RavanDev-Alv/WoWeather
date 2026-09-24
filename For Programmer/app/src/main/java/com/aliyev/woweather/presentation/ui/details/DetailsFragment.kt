package com.aliyev.woweather.presentation.ui.details


import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aliyev.woweather.R
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
import com.aliyev.woweather.domain.model.forecast.HourUiModel
import com.aliyev.woweather.presentation.ui.home.adapters.HourlyAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
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
        setupHourlyTemperatureChart()
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
            updateHourlyTemperatureChart(forecastDayItem?.hour, isFahrenheitSelected)
            val randomAqi = Random.nextInt(0, 100)
            textAqi.animateNumbers(0, randomAqi)
            aqiView.animateProgress(randomAqi.toFloat())
        }
    }

    private fun setupHourlyTemperatureChart() {
        binding.chartHourlyTemperature.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(true)
            setDragEnabled(true)
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            setNoDataText("")

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textColor = ContextCompat.getColor(requireContext(), R.color.grey_49)
                textSize = 10f
                setAvoidFirstLastClipping(true)
            }
            axisLeft.apply {
                setDrawGridLines(true)
                gridColor = ContextCompat.getColor(requireContext(), R.color.white_E4)
                textColor = ContextCompat.getColor(requireContext(), R.color.grey_49)
                textSize = 10f
            }
            axisRight.isEnabled = false
            minOffset = 8f
        }
    }

    private fun updateHourlyTemperatureChart(hours: List<HourUiModel>?, isFahrenheit: Boolean) {
        val chart = binding.chartHourlyTemperature
        val title = binding.textChartTitle
        val card = binding.cardHourlyChart

        val series = hours.orEmpty().mapNotNull { hour ->
            val raw = if (isFahrenheit) hour.tempF else hour.tempC
            if (raw == null) null else hour to raw.toFloat()
        }

        if (series.isEmpty()) {
            chart.clear()
            chart.visibility = View.GONE
            title.visibility = View.GONE
            card.visibility = View.GONE
            return
        }

        chart.visibility = View.VISIBLE
        title.visibility = View.VISIBLE
        card.visibility = View.VISIBLE

        val hourModels = series.map { it.first }
        val temps = series.map { it.second }

        val entries = temps.mapIndexed { index, temp -> Entry(index.toFloat(), temp) }

        val lineColor = ContextCompat.getColor(requireContext(), R.color.blue_4F)
        val dataSet = LineDataSet(entries, "").apply {
            color = lineColor
            setCircleColor(lineColor)
            lineWidth = 2.2f
            circleRadius = 3.5f
            setDrawCircleHole(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.2f
            setDrawFilled(true)
            fillColor = lineColor
            fillAlpha = 50
            setDrawValues(false)
            highLightColor = lineColor
        }

        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val i = value.toInt().coerceIn(0, hourModels.lastIndex)
                return formatHourAxisLabel(hourModels[i].time)
            }
        }

        val minT = temps.minOrNull() ?: 0f
        val maxT = temps.maxOrNull() ?: 0f
        val span = (maxT - minT).coerceAtLeast(2f)
        val pad = span * 0.12f + 0.5f
        chart.axisLeft.axisMinimum = minT - pad
        chart.axisLeft.axisMaximum = maxT + pad

        chart.xAxis.axisMinimum = 0f
        chart.xAxis.axisMaximum = (entries.size - 1).coerceAtLeast(0).toFloat()

        val labelCount = minOf(8, entries.size).coerceAtLeast(2)
        chart.xAxis.setLabelCount(labelCount, false)

        chart.data = LineData(dataSet)
        chart.invalidate()
    }

    private fun formatHourAxisLabel(time: String?): String {
        if (time.isNullOrBlank()) return ""
        val part = time.trim().split(" ").lastOrNull().orEmpty()
        return if (part.length >= 5) part.take(5) else part
    }

}