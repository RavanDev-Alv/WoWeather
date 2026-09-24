package com.aliyev.woweather.common.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aliyev.woweather.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.loadImageUrl(url: String) {
    val options = RequestOptions().centerCrop()
        .error(R.drawable.ic_launcher_background)
        .placeholder(placeHolder(context))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()

    Glide.with(this).load(url).apply(options).into(this)

}

private fun placeHolder(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.setTint(context.getColor(R.color.green_2A))
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun Activity.reset() {
    val packageManager: PackageManager = packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    val componentName = intent?.component
    val mainIntent: Intent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(mainIntent)
    //this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Fragment.setStatusBarColor(color: Int) {
    this.requireActivity().window.statusBarColor = resources.getColor(color, null)
}

fun Fragment.enableTransitionAnimation() {
    val anim =
        TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    sharedElementReturnTransition = anim
    sharedElementEnterTransition = anim
}

fun TextView.animateNumbers(start: Int, finish: Int) {
    val valueAnimator = ValueAnimator.ofInt(start, finish)
    valueAnimator.duration = 1500
    valueAnimator.addUpdateListener { valueAnimator ->
        this.text = valueAnimator.animatedValue.toString()
    }
    valueAnimator.start()
}