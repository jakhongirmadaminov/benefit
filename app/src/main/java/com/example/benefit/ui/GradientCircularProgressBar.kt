package com.example.benefit.ui
//
//  CircularIndicator.swift
//  Benefit
//
//  Created by Doni on 7/14/20.
//  Copyright © 2020 Shin Anatoly. All rights reserved.
//


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.benefit.R
import com.example.benefit.util.SizeUtils
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class GradientCircularProgressBar(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private var progressInPercentage: Float = 0.0F
        set(value) {
            progressAngle = 360F * value
            field = value
        }
    private var progressAngle: Float = 360F * progressInPercentage

    private var pThickness = SizeUtils.dpToPx(context, 40)

    private val innerStrokePaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.BUTT
        strokeWidth = pThickness / 3F
        style = Paint.Style.STROKE
        color = Color.parseColor("#cfcfcf")
    }
    private val shadowColor = Color.parseColor("#4D000000")

    val bgColor = Color.parseColor("#E8E8E8")
    var progressGradientPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = pThickness
        style = Paint.Style.STROKE
    }

    var whiteCirclePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = bgColor
    }
    var baseArcRect = RectF(0F, 0F, 0F, 0F)

    //    private var progressGradientColourStart = 0
//    private var progressGradientColourEnd = 0
    var progressColorList = intArrayOf(
        Color.parseColor("#C165DD"),
        Color.parseColor("#815BF5"),
        Color.parseColor("#FFDA1B"),
        Color.parseColor("#F25353")
    )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GradientCircularProgressBar,
            0, 0
        ).apply {
            try {
                progressInPercentage =
                    getFloat(R.styleable.GradientCircularProgressBar_cgProgress, 0F)
            } finally {
                recycle()
            }
        }
    }

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
        val baseArcLeft = width / 2 - (radius - pThickness)
        val baseArcTop = height / 2 - (radius - pThickness)
        val baseArcRight = width / 2 + (radius - pThickness)
        val baseArcBottom = height / 2 + (radius - pThickness)

        baseArcRect[baseArcLeft, baseArcTop, baseArcRight] = baseArcBottom
        //Recalculate the gradient
        setProgressColourAsGradient(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        drawBg(canvas)

        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(result)

        drawProgress(tempCanvas)

        drawGradientDST_IN(tempCanvas)

        canvas.drawBitmap(result, 0F, 0F, Paint())
        makeStartSpot(canvas)

        putProgressSpot(canvas)
    }

    private fun putProgressSpot(canvas: Canvas) {

        val angle = progressAngle - 90

        val r = width / 2 - pThickness

        val x: Double = width / 2 + r * cos(angle * Math.PI / 180)
        val y: Double = height / 2 + r * sin(angle * Math.PI / 180)


        canvas.drawCircle(
            x.toFloat(),
            y.toFloat(),
            pThickness / 2F,
            Paint().apply {
                setShadowLayer(
                    SizeUtils.dpToPx(context, 3), 0F, 0F, shadowColor
                )
                isAntiAlias = true
                strokeCap = Paint.Cap.BUTT
                strokeWidth = pThickness / 10
                style = Paint.Style.STROKE
                color = Color.WHITE

            }
        )

    }

    private fun drawGradientDST_IN(canvas: Canvas) {
        progressGradientPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawArc(baseArcRect, -90F, 360F, false, progressGradientPaint)
        progressGradientPaint.xfermode = null
    }

    private fun drawBg(canvas: Canvas) {

        //MAIN BG RING
        canvas.drawCircle(width / 2F,
            height / 2F,
            width / 2F - pThickness,
            Paint().apply {
                isAntiAlias = true
                strokeCap = Paint.Cap.BUTT
                strokeWidth = pThickness
                style = Paint.Style.STROKE
                color = bgColor
            })


//INNER OVAL
        canvas.drawCircle(
            width / 2F,
            height / 2F,
            width / 2F - pThickness * 2.1F,
            Paint().apply {
                color = Color.parseColor("#e9e9e9")
                style = Paint.Style.FILL

            }
        )

        //INNER DARK RING

        canvas.drawCircle(
            width / 2F,
            height / 2F,
            (width / 2F - pThickness * 2) + pThickness * 0.2F,
            innerStrokePaint
        )


    }

    private fun drawProgress(canvas: Canvas) {
        canvas.drawArc(baseArcRect, -90F, progressAngle, false, progressGradientPaint)


//        val original = BitmapFactory.decodeResource(context.resources, R.drawable.original_image)
//        val mask = BitmapFactory.decodeResource(context.resources, R.drawable.mask_image)
//
//        //You can change original image here and draw anything you want to be masked on it.
//
//
//        //You can change original image here and draw anything you want to be masked on it.
//        val result = Bitmap.createBitmap(mask.width, mask.height, Bitmap.Config.ARGB_8888)
//        val tempCanvas = Canvas(result)
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
//        tempCanvas.drawBitmap(original, 0F, 0F, null)
//        tempCanvas.drawBitmap(mask, 0F, 0F, paint)
//        paint.xfermode = null

        //Draw result after performing masking

        //Draw result after performing masking
//        canvas.drawBitmap(result, 0F, 0F, Paint())
    }

    fun makeStartSpot(canvas: Canvas) {
        whiteCirclePaint.setShadowLayer(
            SizeUtils.dpToPx(context, 3),
            SizeUtils.dpToPx(context, 3),
            0F,
            shadowColor
        )
        canvas.drawCircle(
            width / 2F,
            height / 2F - width / 2 + pThickness,
            pThickness / 2F,
            whiteCirclePaint
        )
    }

    fun setProgressColourAsGradient(invalidateNow: Boolean) {
        val sweepGradient = SweepGradient(
            baseArcRect.centerX(),
            baseArcRect.centerY(),
            progressColorList,
            floatArrayOf(0.0F, 0.6F, 0.8F, 1.0F)
        )
        //Make the gradient start from 90 degrees
        val matrix = Matrix()
        matrix.setRotate(-90F, baseArcRect.centerX(), baseArcRect.centerY())
        sweepGradient.setLocalMatrix(matrix)
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//            progressStrokePaint.blendMode  =BlendMode.DST_IN
//        }
        progressGradientPaint.shader = sweepGradient

//        progressShaderPaint.setMaskFilter(BlurMaskFilter(50F, BlurMaskFilter.Blur.NORMAL))
//        progressShaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

//        progressStrokePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        if (invalidateNow) {
            invalidate()
        }
    }

    fun getProgressInPercentage(): Float {
        return progressInPercentage
    }

    fun setProgress(progress: Float) {
        progressInPercentage = progress
        invalidate()
        requestLayout()
    }

}
