package com.aliyev.woweather.common.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aliyev.woweather.R

object BindingAdapter {

    @BindingAdapter("load_image")
    @JvmStatic
    fun loadImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            imageView.loadImageUrl(it)
        }
    }

    @BindingAdapter("load_resource")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @BindingAdapter("load_icon")
    @JvmStatic
    fun setImageIcon(imageView: ImageView, url: String?) {
        imageView.loadImageUrl("https:$url")
    }

    @BindingAdapter("app:concatDate")
    @JvmStatic
    fun setDate(textView: TextView, date: String?) {
        date?.let {
            if (it.length > 5) {
                val newDate = it.subSequence(0, it.length - 6)
                textView.text = newDate
            }
        }
    }

    @BindingAdapter("app:concatTime")
    @JvmStatic
    fun setTime(textView: TextView, date: String?) {
        date?.let {
            if (it.length > 5) {
                val newDate = it.subSequence(it.length - 6, it.length)
                textView.text = newDate
            }
        }
    }

    @BindingAdapter("app:refreshTimeText")
    @JvmStatic
    fun setRefreshTime(textView: TextView, date: String?) {
        date?.let {
            if (it.length > 5) {
                val newDate = it.subSequence(it.length - 6, it.length)
                textView.text =
                    "${textView.context.resources.getString(R.string.last_refresh)} $newDate"
            }
        }
    }

}