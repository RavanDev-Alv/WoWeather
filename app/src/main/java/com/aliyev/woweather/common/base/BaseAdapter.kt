package com.aliyev.woweather.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T : Any>(val selected: Boolean = false) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items: ArrayList<T> = ArrayList()

    abstract fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int,
    ): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        onCreateViewHolder(
            parent = parent,
            inflater = LayoutInflater.from(parent.context),
            viewType = viewType
        )

    fun submitData(data: List<T>?, forceUpdate: Boolean = false) {
        if (forceUpdate) {
            items.clear()
            items.addAll(data ?: emptyList())
            notifyDataSetChanged()
        } else {
            val diffCallback = DiffUtilCallback(items, data ?: emptyList())
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            items.clear()
            items.addAll(data ?: emptyList())
            diffResult.dispatchUpdatesTo(this)
        }
    }

    inner class DiffUtilCallback<T>(private val oldList: List<T>, private val newList: List<T>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            newList.getOrNull(newItemPosition).hashCode() == oldList.getOrNull(oldItemPosition)
                .hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if (selected) false else newList.getOrNull(newItemPosition) == oldList.getOrNull(
                oldItemPosition
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}