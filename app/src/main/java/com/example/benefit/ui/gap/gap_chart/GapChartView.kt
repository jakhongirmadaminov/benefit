package com.example.benefit.ui.gap.gap_chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.util.Constants
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.createBitmapWithBorder
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class GapChartView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

    private var chartCanvas: Canvas? = null
    private var playerCount = 0

    var game: GapGameDTO? = null
        set(value) {
            chartCanvas?.let { canvas ->
                value?.let { game ->
                    setupGame(canvas, game)
                    drawBgArcs(canvas)
                    drawProgressArcs(canvas)
                    drawCentreCircleStroke(canvas)
                    drawCentreCircle(canvas)
                }
            }
            field = value
        }

    private fun setupGame(canvas: Canvas, game: GapGameDTO) {

        playerCount = this.game!!.members!!.size
        pricePerPerson = game.summa!!.toInt()


    }


    private inline fun getBitmap(imageURL: String, block: (Bitmap?) -> Unit) {
        try {
            block(
                Glide.with(context)
                    .asBitmap()
                    .load(imageURL)
                    .override(300, Target.SIZE_ORIGINAL)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .apply(RequestOptions().circleCrop())
                    .placeholder(R.drawable.ic_avatar_sample)
                    .error(R.drawable.ic_avatar_sample)
                    .submit()
                    .get()
            )
        } catch (ex: Exception) {
            block(BitmapFactory.decodeResource(resources, R.drawable.ic_avatar_sample))
//            return fromResource(DEFAULT_IMAGE)
        }
    }

    private var pricePerPerson = 0

    var progressGradientPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
//        strokeWidth = pThickness
//        style = Paint.Style.STROKE
        color = Color.GREEN

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
        radiusBgArc = (min(width, height) * .37).toFloat() //
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

        for (i in game!!.members!!.indices) {
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
        chartCanvas = canvas
        game?.let {
            setupGame(canvas, it)
        }
        drawBgArcs(canvas)
        drawProgressArcs(canvas)
        drawCentreCircleStroke(canvas)
        drawCentreCircle(canvas)
        game?.members!!.forEachIndexed { index, member ->
            getBitmap(Constants.BASE_URL + member.userInfo!!.avatarLink!!.removeSuffix("/")) {
                it?.let {
                    drawAvatarsBitmaps(index, canvas, it.createBitmapWithBorder(10f, Color.WHITE))
                }
            }
        }
        requestLayout()
    }

    private fun drawAvatarsBitmaps(position: Int, canvas: Canvas, bitmap: Bitmap) {
        val startAngle = 360 / playerCount * position.toFloat() - 90
        val new_x = (radiusBgArc + 14) * cos(startAngle * Math.PI / 180).toFloat()
        val new_y = (radiusBgArc + 14) * sin(startAngle * Math.PI / 180).toFloat()

        canvas.drawBitmap(
            bitmap,
            null,
            RectF(
                baseRect.centerX() + new_x - (progressArcRadius - radiusBgArc - pieMargin * 2),
                baseRect.centerY() + new_y - (progressArcRadius - radiusBgArc - pieMargin * 2),
                baseRect.centerX() + new_x + (progressArcRadius - radiusBgArc - pieMargin * 2),
                baseRect.centerY() + new_y + (progressArcRadius - radiusBgArc - pieMargin * 2),
            ),
            Paint().apply {
                isAntiAlias = true
                color = Color.WHITE
            }
        )
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

    var hasOffsetSetup = false
    private fun drawBgArcs(canvas: Canvas) {

        if (!hasOffsetSetup) {
            for (i in game!!.members!!.indices) {
                val startAngle = 360 / playerCount * i.toFloat() - 90
                val endAngle = 360 / playerCount * (i + 1).toFloat() - 90
                val midAngle = startAngle + ((endAngle - startAngle) / 2)
                val new_x =
                    SizeUtils.dpToPx(context, pieMargin) * cos(midAngle * Math.PI / 180).toFloat()
                val new_y =
                    SizeUtils.dpToPx(context, pieMargin) * sin(midAngle * Math.PI / 180).toFloat()
                bgArcRects[i].offset(new_x, new_y)
            }
            hasOffsetSetup = true
        }

        for (i in game!!.members!!.indices) {
            canvas.drawArc(
                bgArcRects[i],
                360 / playerCount * i.toFloat() - 90,
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

            if (i == 0 || i == 1) {

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
        }
    }
}