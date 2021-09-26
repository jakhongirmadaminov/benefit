package com.example.benefit.ui.gap.gap_chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.benefit.R
import com.example.benefit.util.SizeUtils
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class GapChartView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {


    var playerCount = 6
        set(value) {
            requestLayout()
            field = value
        }


    var pricePerPerson = 100_000
        set(value) {
            requestLayout()
            field = value
        }

    var progressGradientPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
//        strokeWidth = pThickness
//        style = Paint.Style.STROKE
        color = Color.GREEN

    }
    var redPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
//        strokeWidth = pThickness
//        style = Paint.Style.STROKE
        color = Color.RED

    }
    var progressArcRect = RectF(0F, 0F, 0F, 0F)
    var baseRect = RectF(0F, 0F, 0F, 0F)
    var bgArchRect = RectF(0F, 0F, 0F, 0F)

    val bgArcRects = arrayListOf<RectF>()
    val progressArcRects = arrayListOf<RectF>()

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
        setMeasuredDimension(width, width)
    }

    var radiusBgArc = 0f
    var progressArcRadius = 0f
    var radiusBase = 0f
    var baseArcLeft = 0f
    var baseArcTop = 0f
    var baseArcRight = 0f
    var baseArcBottom = 0f
    var pieMargin = 4

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        //Ensures arc is within the rectangle
        progressArcRadius =
            (min(width, height) * .5 - SizeUtils.dpToPx(context, pieMargin)).toFloat() //
        radiusBgArc = (min(width, height) * .4).toFloat() //
        radiusBase = (min(width, height) * .5).toFloat() //

        //I do radius - thickness so that the arc is within the rectangle
        baseArcLeft = width / 2 - progressArcRadius
        baseArcTop = height / 2 - progressArcRadius
        baseArcRight = width / 2 + progressArcRadius
        baseArcBottom = height / 2 + progressArcRadius

        progressArcRect[baseArcLeft, baseArcTop, baseArcRight] = baseArcBottom
        baseRect = RectF(
            width / 2 - radiusBase,
            height / 2 - radiusBase,
            width / 2 + radiusBase,
            height / 2 + radiusBase,
        )
        bgArchRect[width / 2 - radiusBgArc, height / 2 - radiusBgArc, width / 2 + radiusBgArc] =
            height / 2 + radiusBgArc

        for (i in 0..playerCount) {
            bgArcRects.add(
                RectF(
                    bgArchRect.left,
                    bgArchRect.top,
                    bgArchRect.right,
                    bgArchRect.bottom
                )
            )
            progressArcRects.add(
                RectF(
                    progressArcRect.left,
                    progressArcRect.top,
                    progressArcRect.right,
                    progressArcRect.bottom
                )
            )

        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBgArcs(canvas)
        drawProgressArcs(canvas)
        drawCentreCircleStroke(canvas)
        drawCentreCircle(canvas)
        drawPlayerAvatarsBg(canvas)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.rectangle_photo_four)


        drawAvatarsBitmaps(canvas, listOf(bitmap, bitmap, bitmap, bitmap, bitmap, bitmap))
    }

    private fun drawAvatarsBitmaps(canvas: Canvas, bitmaps: List<Bitmap>) {
        for (i in 0 until playerCount) {
            val startAngle = 360 / playerCount * i.toFloat() - 90
            val new_x = radiusBgArc * cos(startAngle * Math.PI / 180).toFloat()
            val new_y = radiusBgArc * sin(startAngle * Math.PI / 180).toFloat()

            canvas.drawBitmap(
                bitmaps[i],
                null,
                RectF(
                    baseRect.centerX() + new_x - (progressArcRadius - radiusBgArc - pieMargin*2),
                    baseRect.centerY() + new_y - (progressArcRadius - radiusBgArc - pieMargin*2),
                    baseRect.centerX() + new_x + (progressArcRadius - radiusBgArc - pieMargin*2),
                    baseRect.centerY() + new_y + (progressArcRadius - radiusBgArc - pieMargin*2),
                ),
                Paint().apply {
                    isAntiAlias = true
                    color = Color.WHITE
                }
            )
        }

    }

    private fun drawPlayerAvatarsBg(canvas: Canvas) {
        for (i in 0 until playerCount) {
            val startAngle = 360 / playerCount * i.toFloat() - 90
            val new_x = radiusBgArc * cos(startAngle * Math.PI / 180).toFloat()
            val new_y = radiusBgArc * sin(startAngle * Math.PI / 180).toFloat()
            canvas.drawCircle(
                baseRect.centerX() + new_x,
                baseRect.centerY() + new_y,
                progressArcRadius - radiusBgArc,
                Paint().apply {
                    isAntiAlias = true
                    color = Color.WHITE
                }
            )
        }
    }

    private fun drawCentreCircleStroke(canvas: Canvas) {
        val circleRadius = width * .15f
        canvas.drawCircle(
            baseRect.centerX(),
            baseRect.centerY(),
            circleRadius + SizeUtils.dpToPx(context, 5),
            Paint().apply {
                isAntiAlias = true
                color = ContextCompat.getColor(context, R.color.window_background)
            })
    }

    private fun drawCentreCircle(canvas: Canvas) {

        val circleRadius = width * .15f
        canvas.drawCircle(
            baseRect.centerX(),
            baseRect.centerY(),
            circleRadius,
            Paint().apply {
                isAntiAlias = true
                color = Color.GRAY
                shader = LinearGradient(
                    baseRect.centerX(),
                    baseRect.centerY() + circleRadius,
                    baseRect.centerX(),
                    baseRect.centerY() - circleRadius,
                    0xffFFE98A.toInt(),
                    0xffD74177.toInt(),
                    Shader.TileMode.CLAMP
                )
            })
    }

    private fun drawBgArcs(canvas: Canvas) {

        for (i in 0 until playerCount) {
            val startAngle = 360 / playerCount * i.toFloat() - 90
            val endAngle = 360 / playerCount * (i + 1).toFloat() - 90
            val midAngle = startAngle + ((endAngle - startAngle) / 2)

            val new_x =
                SizeUtils.dpToPx(context, pieMargin) * cos(midAngle * Math.PI / 180).toFloat()
            val new_y =
                SizeUtils.dpToPx(context, pieMargin) * sin(midAngle * Math.PI / 180).toFloat()

            bgArcRects[i].offset(new_x, new_y)

            canvas.drawArc(
                bgArcRects[i],
                startAngle,
                360 / playerCount.toFloat(),
                true,
                Paint().apply {
                    isAntiAlias = true
                    color = Color.WHITE
                }
            )
        }
    }

    private fun drawProgressArcs(canvas: Canvas) {

        for (i in 0 until playerCount) {

//            if (i == 0 || i == 1) {

            val startAngle = 360 / playerCount * i.toFloat() - 90
            val endAngle = 360 / playerCount * (i + 1).toFloat() - 90

            val gradient =
                SweepGradient(
                    progressArcRects[i].centerX(),
                    progressArcRects[i].centerY(),
                    Color.parseColor("#602b8f"),
                    Color.parseColor("#FAB3E3")
                )

            val matrix = Matrix()
            matrix.postRotate(
                endAngle,
                progressArcRects[i].centerX(),
                progressArcRects[i].centerY()
            )
            gradient.setLocalMatrix(matrix)
            progressGradientPaint.shader = gradient
            canvas.drawArc(
                progressArcRects[i],
                startAngle,
                360 / playerCount.toFloat(),
                true,
                progressGradientPaint
            )
        }
//        }
    }

//    private fun drawBg(canvas: Canvas) {
//
//        //MAIN BG RING
//        canvas.drawCircle(width / 2F,
//            height / 2F,
//            width / 2F - pThickness,
//            Paint().apply {
//                isAntiAlias = true
//                strokeCap = Paint.Cap.BUTT
//                strokeWidth = pThickness
//                style = Paint.Style.STROKE
//                color = bgColor
//            })
//
//
////INNER OVAL
//        canvas.drawCircle(
//            width / 2F,
//            height / 2F,
//            width / 2F - pThickness * 2.1F,
//            Paint().apply {
//                color = Color.parseColor("#e9e9e9")
//                style = Paint.Style.FILL
//
//            }
//        )
//
//        //INNER DARK RING
//
//        canvas.drawCircle(
//            width / 2F,
//            height / 2F,
//            (width / 2F - pThickness * 2) + pThickness * 0.2F,
//            innerStrokePaint
//        )
//
//
//    }

}