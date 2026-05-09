package com.aliyev.woweather.common.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.aliyev.woweather.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class WindCompassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var faceShader: Shader? = null
    private var lastShaderKey: Int = 0
    private var windDegrees: Float = 0f

    private val facePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val ringOuterPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val ringInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val tickMajorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val tickMinorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val labelNorthPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        letterSpacing = 0.06f
    }
    private val labelOtherPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        letterSpacing = 0.06f
    }
    private val arrowHeadPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val arrowTailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val arrowHubPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val arrowHubStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        ringOuterPaint.strokeWidth = dp(1.1f)
        ringInnerPaint.strokeWidth = dp(0.85f)
        tickMajorPaint.strokeWidth = dp(1.35f)
        tickMinorPaint.strokeWidth = dp(0.9f)
        labelNorthPaint.textSize = sp(11f)
        labelOtherPaint.textSize = sp(11f)
        arrowHubStrokePaint.strokeWidth = dp(2f)

        ringOuterPaint.color = ContextCompat.getColor(context, R.color.wind_compass_ring)
        ringInnerPaint.color = ContextCompat.getColor(context, R.color.wind_compass_ring_inner)
        tickMajorPaint.color = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(context, R.color.black_20), 100
        )
        tickMinorPaint.color = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(context, R.color.black_20), 55
        )
        labelNorthPaint.color = ContextCompat.getColor(context, R.color.black_20)
        labelOtherPaint.color = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(context, R.color.black_20), 150
        )
        arrowHeadPaint.color = ContextCompat.getColor(context, R.color.wind_arrow_head)
        arrowTailPaint.color = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(context, R.color.wind_arrow_tail), 200
        )
        arrowHubPaint.color = ContextCompat.getColor(context, R.color.wind_compass_face_center)
        arrowHubStrokePaint.color = ContextCompat.getColor(context, R.color.wind_arrow_head)
    }

    fun setWindDirection(degrees: Float) {
        windDegrees = degrees % 360f
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val cx = w / 2f
        val cy = h / 2f
        val r = min(w, h) / 2f - dp(14f)

        val key = (r * 1000f).toInt()
        if (faceShader == null || key != lastShaderKey) {
            lastShaderKey = key
            val c0 = ContextCompat.getColor(context, R.color.wind_compass_face_center)
            val c1 = ContextCompat.getColor(context, R.color.wind_compass_face_edge)
            faceShader = RadialGradient(
                cx,
                cy - r * 0.08f,
                r * 1.08f,
                intArrayOf(c0, c1),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP,
            )
        }
        facePaint.shader = faceShader
        canvas.drawCircle(cx, cy, r, facePaint)
        facePaint.shader = null

        canvas.drawCircle(cx, cy, r - dp(0.5f), ringOuterPaint)
        canvas.drawCircle(cx, cy, r - dp(10f), ringInnerPaint)

        for (deg in 0 until 360 step 15) {
            val rad = Math.toRadians((-90 + deg).toDouble())
            val ox = (cos(rad) * (r - dp(2f))).toFloat()
            val oy = (sin(rad) * (r - dp(2f))).toFloat()
            val isCardinal = deg % 90 == 0
            val isInter = deg % 45 == 0 && !isCardinal
            val innerR = when {
                isCardinal -> r - dp(13f)
                isInter -> r - dp(9f)
                else -> r - dp(6f)
            }
            val ix = (cos(rad) * innerR).toFloat()
            val iy = (sin(rad) * innerR).toFloat()
            val p = if (isCardinal || isInter) tickMajorPaint else tickMinorPaint
            canvas.drawLine(cx + ix, cy + iy, cx + ox, cy + oy, p)
        }

        val labelR = r - dp(22f)
        cardinals.forEach { (deg, label) ->
            val rad = Math.toRadians((-90 + deg).toDouble())
            val lx = cx + (cos(rad) * labelR).toFloat()
            val ly = cy + (sin(rad) * labelR).toFloat()
            val paint = if (deg == 0) labelNorthPaint else labelOtherPaint
            canvas.drawText(label, lx, ly + paint.textSize * 0.32f, paint)
        }

        drawWindArrow(canvas, cx, cy, r)
    }

    private fun drawWindArrow(canvas: Canvas, cx: Float, cy: Float, r: Float) {
        canvas.save()
        canvas.rotate(windDegrees, cx, cy)

        val headLen = r * 0.50f
        val tailLen = r * 0.46f
        val headHalf = r * 0.095f
        val shaftW = r * 0.040f
        val hubR = r * 0.060f

        canvas.drawRect(
            cx - shaftW, cy,
            cx + shaftW, cy + tailLen,
            arrowTailPaint,
        )

        val headPath = Path().apply {
            moveTo(cx, cy - headLen)
            lineTo(cx - headHalf, cy - headLen * 0.35f)
            lineTo(cx, cy)
            lineTo(cx + headHalf, cy - headLen * 0.35f)
            close()
        }
        canvas.drawPath(headPath, arrowHeadPaint)

        canvas.drawCircle(cx, cy, hubR, arrowHubPaint)
        canvas.drawCircle(cx, cy, hubR, arrowHubStrokePaint)

        canvas.restore()
    }

    private fun dp(v: Float) = v * resources.displayMetrics.density

    private fun sp(v: Float) = v * resources.displayMetrics.scaledDensity

    companion object {
        private val cardinals = listOf(0 to "N", 90 to "E", 180 to "S", 270 to "W")
    }
}
