package com.example.benefit.ui//
//  CircularIndicator.swift
//  Benefit
//
//  Created by Doni on 7/14/20.
//  Copyright © 2020 Shin Anatoly. All rights reserved.
//


import android.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.benefit.util.CanvasUtil
import com.example.benefit.util.SizeUtils


class GradientCircularProgressBar(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    /**
     * Thickness of the arc
     */
    private var thickness = 0F
    lateinit var progressFillPaint: Paint
    lateinit var baseArcRect: RectF
    private var progressGradientColourStart = 0
    private var progressGradientColourEnd = 0

    init {
        progressGradientColourStart =
            ContextCompat.getColor(context, R.color.white)
        progressGradientColourEnd = ContextCompat.getColor(context, R.color.black)
        thickness = SizeUtils.dpToPx(context, 25)

        //We do not want a colour for this because we will set a gradient

        //We do not want a colour for this because we will set a gradient
        progressFillPaint = CanvasUtil.makeStrokePaint(SizeUtils.dpToPx(context, 25), -1)
        baseArcRect = RectF(0F, 0F, 0F, 0F)
        setProgressColourAsGradient(false)


    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //Ensures arc is within the rectangle

        //Ensures arc is within the rectangle
        val radius = (Math.min(width, height) / 2).toFloat() //


        //I do radius - thickness so that the arc is within the rectangle

        //I do radius - thickness so that the arc is within the rectangle
        val baseArcLeft = width / 2 - (radius - thickness)
        val baseArcTop = height / 2 - (radius - thickness)
        val baseArcRight = width / 2 + (radius - thickness)
        val baseArcBottom = height / 2 + (radius - thickness)

        baseArcRect!![baseArcLeft, baseArcTop, baseArcRight] = baseArcBottom
        //Recalculate the gradient
        //Recalculate the gradient
        setProgressColourAsGradient(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(baseArcRect!!, 135F, 270F, false, progressFillPaint!!)

    }

    fun setProgressColourAsGradient(invalidateNow: Boolean) {
        val sweepGradient = SweepGradient(
            baseArcRect!!.centerX(),
            baseArcRect!!.centerY(),
            progressGradientColourStart,
            progressGradientColourEnd
        )
        //Make the gradient start from 90 degrees
        val matrix = Matrix()
        matrix.setRotate(90F, baseArcRect!!.centerX(), baseArcRect!!.centerY())
        sweepGradient.setLocalMatrix(matrix)
        progressFillPaint!!.shader = sweepGradient
        if (invalidateNow) {
            invalidate()
        }
    }

}
