package com.example.benefit.ui//
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
import com.example.benefit.util.CanvasUtil
import com.example.benefit.util.SizeUtils
import kotlin.math.min


class GradientCircularProgressBar(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    /**
     * Thickness of the arc
     */
    private var thickness = SizeUtils.dpToPx(context, 40)
    var progressGradientPaint = CanvasUtil.makeStrokePaint(thickness, -1)
    var progressShaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var whiteCirclePaint = CanvasUtil.makeFillPaint(Color.parseColor("#E8E5E5"))
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
//        progressGradientColourStart = Color.parseColor("#F25353")
//        progressGradientColourEnd = Color.parseColor("#C165DD")

        //We do not want a colour for this because we will set a gradient

        //We do not want a colour for this because we will set a gradient

        setProgressColourAsGradient(false)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //Ensures arc is within the rectangle

        //Ensures arc is within the rectangle
        val radius = (min(width, height) / 2).toFloat() //


        //I do radius - thickness so that the arc is within the rectangle

        //I do radius - thickness so that the arc is within the rectangle
        val baseArcLeft = width / 2 - (radius - thickness)
        val baseArcTop = height / 2 - (radius - thickness)
        val baseArcRight = width / 2 + (radius - thickness)
        val baseArcBottom = height / 2 + (radius - thickness)

        baseArcRect[baseArcLeft, baseArcTop, baseArcRight] = baseArcBottom
        //Recalculate the gradient
        //Recalculate the gradient
        setProgressColourAsGradient(false)
    }

    override fun onDraw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val original = Canvas(bitmap)




        canvas.drawArc(baseArcRect, -90F, 360F, false, progressGradientPaint)
        makeStartSpot(canvas)


        super.onDraw(original)

        canvas.drawBitmap(bitmap, 0F, 0F, null)
        overlayGray(canvas)

        val maskPaint = Paint()
        maskPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.drawBitmap(mask, 0F, 0F, maskPaint)
    }

    private fun overlayGray(canvas: Canvas) {
        canvas.drawArc(baseArcRect, -90F, 320F, false, progressShaderPaint)


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
            Color.parseColor("#4D000000")
        )
        canvas.drawCircle(
            width / 2F,
            height / 2F - width / 2 + thickness,
            thickness / 2F,
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

}
