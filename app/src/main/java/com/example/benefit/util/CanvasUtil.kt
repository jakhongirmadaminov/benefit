package com.example.benefit.util

import android.graphics.Paint

object CanvasUtil {
    fun makeStrokePaint(width: Float, color: Int): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.SQUARE
        paint.strokeWidth = width.toFloat()
        paint.style = Paint.Style.STROKE
        paint.color = color
        return paint
    }

    fun makeFillPaint(color: Int): Paint {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = color
        return paint
    }
}