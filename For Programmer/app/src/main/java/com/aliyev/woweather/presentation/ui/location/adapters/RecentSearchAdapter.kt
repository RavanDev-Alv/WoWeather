package com.aliyev.woweather.presentation.ui.location.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.databinding.ItemRecentSearchBinding
import com.aliyev.woweather.domain.model.local.RecentSearchesUiModel

class RecentSearchAdapter : BaseAdapter<RecentSearchesUiModel>() {

    var onClickRecentItem: (RecentSearchesUiModel) -> Unit = {}

    inner class RecentSearchVH(private val binding: ItemRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearchesUiModel) {
            with(binding) {
                data = item
                executePendingBindings()

                cardMain.setOnClickListener {
                    onClickRecentItem(item)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        RecentSearchVH(ItemRecentSearchBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentSearchVH -> {
                items.getOrNull(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }
}