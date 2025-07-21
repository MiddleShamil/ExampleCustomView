package com.example.customviewsurf

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import kotlin.random.Random

class ExampleCustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAtr: Int = 0
) : View(context, attributeSet, defStyleAtr) {

    private val shapes = mutableListOf<ShapeData>()
    private var colors = listOf<Int>()
    private var defaultColor = Color.GREEN
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 50f
        style = Paint.Style.FILL
    }
    private val shapePaint = Paint()

    private val minSizeDp = 20f
    private val maxSizeDp = 50f
    private var minSizePx = 0f
    private var maxSizePx = 0f

    init {
        context.withStyledAttributes(attributeSet, R.styleable.ExampleCustomView, defStyleAtr, 0) {
            defaultColor = getColor(R.styleable.ExampleCustomView_customPaintColor, Color.GREEN)
        }

        minSizePx = minSizeDp * resources.displayMetrics.density
        maxSizePx = maxSizeDp * resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        shapes.forEach { shape ->
            shapePaint.color = shape.color
            when (shape.type) {
                ShapeType.CIRCLE -> canvas.drawCircle(shape.x, shape.y, shape.size / 2, shapePaint)
                ShapeType.SQUARE -> canvas.drawRect(
                    shape.x - shape.size / 2,
                    shape.y - shape.size / 2,
                    shape.x + shape.size / 2,
                    shape.y + shape.size / 2,
                    shapePaint
                )

                ShapeType.ROUNDED_SQUARE -> canvas.drawRoundRect(
                    shape.x - shape.size / 2,
                    shape.y - shape.size / 2,
                    shape.x + shape.size / 2,
                    shape.y + shape.size / 2,
                    shape.size / 8,
                    shape.size / 8,
                    shapePaint
                )
            }
        }

        canvas.drawText("${shapes.size}", 50f, 100f, textPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            addShape(event.x, event.y)
            invalidate()
            return true
        }

        return super.onTouchEvent(event)
    }

    private fun addShape(x: Float, y: Float) {
        if (shapes.size >= 10) {
            shapes.clear()
            Toast.makeText(context, "Game over", Toast.LENGTH_SHORT).show()
        }

        val type = ShapeType.entries.toTypedArray().random()
        val color = if (this.colors.isNotEmpty()) {
            colors.random()
        } else {
            defaultColor
        }
        val size = (minSizePx..maxSizePx).random()

        shapes.add(ShapeData(x, y, size, color, type))
    }

    fun setColors(colors: List<Int>) {
        this.colors = colors
    }

    fun setHexColors(colors: List<String>) {
        this.colors = colors.map { it.toColorInt() }
    }
}

fun ClosedFloatingPointRange<Float>.random(): Float {
    return this.start + Random.nextFloat() * (this.endInclusive - this.start)
}

enum class ShapeType {
    CIRCLE, SQUARE, ROUNDED_SQUARE
}

data class ShapeData(
    val x: Float,
    val y: Float,
    val size: Float,
    val color: Int,
    val type: ShapeType
)