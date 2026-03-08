package com.aliyev.woweather.common.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.aliyev.woweather.R
import com.aliyev.woweather.databinding.LoadingLayoutBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun navigationTextFont(view: View) {
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            navigationTextFont(child)
        }
    } else if (view is TextView) {
        view.typeface = ResourcesCompat.getFont(view.context, R.font.sf_semi_bold)
    }
}

fun progressDialog(context: Context): Dialog {
    val dialog = Dialog(context)
    val layout = LoadingLayoutBinding.inflate(LayoutInflater.from(context))
    dialog.setContentView(layout.root)
    dialog.setCancelable(false)

    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

fun makeCityToken(id: Int): String = "id:$id"

fun getTime(): String = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())

