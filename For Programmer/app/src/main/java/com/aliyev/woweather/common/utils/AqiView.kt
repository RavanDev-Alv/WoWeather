package com.aliyev.woweather.common.utils

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.aliyev.woweather.R

class AqiView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        const val ARC_FULL_ROTATION_DEGREE = 300
        const val PERCENTAGE_DIVIDER = 100.0
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private var currentPercentage = 0


    private val ovalSpace = RectF()

    private val parentArcColor =
        context?.resources?.getColor(R.color.white_E4, null) ?: Color.GRAY
    private val fillArcColor = context?.resources?.getColor(R.color.green_2A, null) ?: Color.GREEN

    private val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 20f
    }

    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = fillArcColor
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        setSpace()
        drawBackgroundArc(canvas)
        drawInnerArc(canvas)

    }

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 100
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun drawBackgroundArc(it: Canvas) {
        it.drawArc(ovalSpace, 120f, 300f, false, parentArcPaint)
    }

    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, 120f, percentageToFill, false, fillArcPaint)
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    fun animateProgress(finishPercentage: Float) {
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, finishPercentage)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 1500
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
    }
}
