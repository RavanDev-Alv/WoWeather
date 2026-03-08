package com.aliyev.woweather.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.databinding.ItemHourlyForecastBinding
import com.aliyev.woweather.domain.model.forecast.HourUiModel

class HourlyAdapter : BaseAdapter<HourUiModel>() {

    var onClickHourlyItem: (HourUiModel) -> Unit = {}

    inner class HourlyViewHolder(private val binding: ItemHourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HourUiModel) {
            with(binding) {
                data = item
                executePendingBindings()

                cardHourly.setOnClickListener {
                    onClickHourlyItem(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        HourlyViewHolder(ItemHourlyForecastBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HourlyViewHolder -> {
                items.getOrNull(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }
}