package com.aliyev.woweather.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.databinding.ItemDailyForecastBinding
import com.aliyev.woweather.domain.model.forecast.ForecastdayUiModel

class DailyAdapter : BaseAdapter<ForecastdayUiModel>() {

    var onClickDailyItem: (ForecastdayUiModel) -> Unit = {}

    inner class DailyViewHolder(private val binding: ItemDailyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ForecastdayUiModel) {
            with(binding) {
                data = item
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