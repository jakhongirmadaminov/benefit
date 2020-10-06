package com.example.benefit.ui.gap.gap_chart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class GapChartView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {


    var playerCount = 4

    var progressGradientPaint = Paint().apply {
        isAntiAlias = true
//        strokeCap = Paint.Cap.ROUND
//        strokeWidth = pThickness
//        style = Paint.Style.STROKE
        color = Color.WHITE
    }
    var baseArcRect = RectF(0F, 0F, 0F, 0F)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = 400
        val desiredHeight = 400

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width

        //Measure Width
        width = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height

        //Measure Height
        height = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS

        //MUST CALL THIS
        setMeasuredDimension(width, width)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //Ensures arc is within the rectangle
        val radius = (min(width, height) / 2).toFloat() //

        //I do radius - thickness so that the arc is within the rectangle
        val baseArcLeft = width / 2 - radius
        val baseArcTop = height / 2 - radius
        val baseArcRight = width / 2 + radius
        val baseArcBottom = height / 2 + radius

        baseArcRect[baseArcLeft, baseArcTop, baseArcRight] = baseArcBottom

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)



//        for (i in 0 until playerCount) {
//            canvas.drawArc(baseArcRect, -90F, 80F, true, progressGradientPaint)
//        }

    }

}