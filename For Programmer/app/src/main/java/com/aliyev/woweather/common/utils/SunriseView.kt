package com.aliyev.woweather.common.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class SunriseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var sunriseMinutes: Int = 6 * 60 + 14
    private var sunsetMinutes: Int = 20 * 60 + 42
    private var currentMinutes: Int = 12 * 60

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 0f
        color = Color.argb(45, 255, 255, 255)
        pathEffect = android.graphics.DashPathEffect(floatArrayOf(dp(3f), dp(5f)), 0f)
    }
    private val horizonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.argb(38, 255, 255, 255)
    }
    private val progressArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#F6B848")
    }
    private val dotDimPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.argb(90, 255, 255, 255)
    }
    private val sunPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFE070")
    }
    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun setSunrise(hourOfDay: Int, minute: Int) {
        sunriseMinutes = hourOfDay * 60 + minute
        invalidate()
    }

    fun setSunset(hourOfDay: Int, minute: Int) {
        sunsetMinutes = hourOfDay * 60 + minute
        invalidate()
    }

    fun setCurrentTime(hourOfDay: Int, minute: Int) {
        currentMinutes = hourOfDay * 60 + minute
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        val padH = dp(18f)
        val arcWidth = w - padH * 2
        val cx = padH + arcWidth / 2f
        val baseY = h - dp(28f)
        val rx = arcWidth / 2f
        val ry = h - dp(52f)

        val riseX = padH
        val setX = w - padH

        val dayLen = (sunsetMinutes - sunriseMinutes).toFloat()
        val isDay = currentMinutes in sunriseMinutes..sunsetMinutes
        val progress = if (isDay) (currentMinutes - sunriseMinutes) / dayLen else -1f

        val oval = RectF(cx - rx, baseY - ry, cx + rx, baseY + ry)

        arcPaint.strokeWidth = dp(1.5f)
        canvas.drawArc(oval, 180f, 180f, false, arcPaint)

        horizonPaint.strokeWidth = dp(1f)
        canvas.drawLine(riseX - dp(4f), baseY, setX + dp(4f), baseY, horizonPaint)

        if (isDay && progress >= 0f) {
            val sunAngleRad = Math.toRadians((180.0 + progress * 180.0))
            val sunX = (cx + cos(sunAngleRad) * rx).toFloat()
            val sunY = (baseY + sin(sunAngleRad) * ry).toFloat()

            val fillPath = Path().apply {
                moveTo(riseX, baseY)
                addArc(oval, 180f, progress * 180f)
                lineTo(sunX, baseY)
                close()
            }
            fillPaint.shader = LinearGradient(
                cx, baseY - ry, cx, baseY,
                Color.argb(56, 246, 184, 72),
                Color.argb(10, 246, 184, 72),
                Shader.TileMode.CLAMP,
            )
            canvas.drawPath(fillPath, fillPaint)

            glowPaint.shader = RadialGradient(
                sunX, sunY, dp(14f),
                Color.argb(127, 255, 210, 80),
                Color.argb(0, 255, 210, 80),
                Shader.TileMode.CLAMP,
            )
            canvas.drawCircle(sunX, sunY, dp(14f), glowPaint)
            canvas.drawCircle(sunX, sunY, dp(7f), sunPaint)
        } else {
            fillPaint.shader = LinearGradient(
                cx, baseY - ry, cx, baseY,
                Color.argb(30, 246, 184, 72),
                Color.argb(5, 246, 184, 72),
                Shader.TileMode.CLAMP,
            )
            val fullPath = Path().apply {
                moveTo(riseX, baseY)
                addArc(oval, 180f, 180f)
                lineTo(setX, baseY)
                close()
            }
            canvas.drawPath(fullPath, fillPaint)
        }

        canvas.drawCircle(riseX, baseY, dp(3f), dotPaint)
        canvas.drawCircle(
            setX,
            baseY,
            dp(3f),
            if (!isDay && currentMinutes > sunsetMinutes) dotPaint else dotDimPaint
        )
    }

    private fun dp(v: Float) = v * resources.displayMetrics.density

    private fun parseTimeString(timeStr: String): Pair<Int, Int>? {
        return try {
            val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.ENGLISH)
            val date = sdf.parse(timeStr) ?: return null
            val cal = java.util.Calendar.getInstance().apply { time = date }
            cal.get(java.util.Calendar.HOUR_OF_DAY) to cal.get(java.util.Calendar.MINUTE)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setSunriseFromString(timeStr: String) {
        parseTimeString(timeStr)?.let { (h, m) -> setSunrise(h, m) }
    }

    fun setSunsetFromString(timeStr: String) {
        parseTimeString(timeStr)?.let { (h, m) -> setSunset(h, m) }
    }

    fun setCurrentTimeFromDateTime(dateTimeStr: String) {
        try {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.ENGLISH)
            val date = sdf.parse(dateTimeStr) ?: return
            val cal = java.util.Calendar.getInstance().apply { time = date }
            setCurrentTime(
                cal.get(java.util.Calendar.HOUR_OF_DAY),
                cal.get(java.util.Calendar.MINUTE)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}