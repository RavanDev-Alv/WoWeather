package com.aliyev.woweather.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.databinding.ItemDailyForecastBinding
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel.Companion.getTemperature

class DailyAdapter : BaseAdapter<ForecastdayUiModel>() {

    var onClickDailyItem: (ForecastdayUiModel) -> Unit = {}

    var isFahrenheitSelected = false

    inner class DailyViewHolder(private val binding: ItemDailyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastdayUiModel) {
            with(binding) {
                data = item
                textView8.text = item.getTemperature(isFahrenheitSelected)
                executePendingBindings()

                buttonGo.setOnClickListener {
                    onClickDailyItem(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        DailyViewHolder(ItemDailyForecastBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailyViewHolder -> {
                items.getOrNull(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }
}