package com.aliyev.woweather.presentation.ui.location.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.databinding.ItemSearchLocationBinding
import com.aliyev.woweather.domain.model.location.SearchLocationUiModel

class SearchLocationAdapter : BaseAdapter<SearchLocationUiModel>() {

    var onClickSearchItem: (SearchLocationUiModel) -> Unit = {}

    inner class SearchLocationVH(private val binding: ItemSearchLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchLocationUiModel) {
            with(binding) {
                data = item
                executePendingBindings()

                cardSearch.setOnClickListener {
                    onClickSearchItem(item)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        SearchLocationVH(ItemSearchLocationBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchLocationVH -> {
                items.getOrNull(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }

}