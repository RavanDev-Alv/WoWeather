package com.aliyev.woweather.presentation.ui.location.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.aliyev.woweather.R
import com.aliyev.woweather.common.base.BaseAdapter
import com.aliyev.woweather.common.utils.gone
import com.aliyev.woweather.common.utils.visible
import com.aliyev.woweather.databinding.ItemSavedLocationBinding
import com.aliyev.woweather.domain.model.local.SavedCityUiModel

class SavedLocationAdapter : BaseAdapter<SavedCityUiModel>(selected = true) {

    var onClickLocationItem: (SavedCityUiModel) -> Unit = {}

    var onClickDeleteLocation: (SavedCityUiModel) -> Unit = {}

    inner class SavedLocationVH(private val binding: ItemSavedLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedCityUiModel) {
            with(binding) {
                data = item
                executePendingBindings()

                cardLocation.setOnClickListener {
                    onClickLocationItem(item)
                }

                cardLocation.setOnLongClickListener {
                    if (items.size > 1) {
                        if (!buttonDelete.isVisible) {
                            buttonDelete.visible()
                            buttonDelete.animation =
                                AnimationUtils.loadAnimation(it.context, R.anim.anim_left)
                        } else {
                            buttonDelete.animation =
                                AnimationUtils.loadAnimation(it.context, R.anim.anim_right)
                            buttonDelete.gone()
                        }
                    }
                    return@setOnLongClickListener true
                }

                buttonDelete.setOnClickListener {
                    onClickDeleteLocation(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        SavedLocationVH(ItemSavedLocationBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SavedLocationVH -> {
                items.getOrNull(position)?.let { item ->
                    holder.bind(item)
                }
            }
        }
    }
}