package com.example.benefit.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue


object SizeUtils {

    fun dpToPx(context: Context, dp: Int): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun pxToDp(context: Context, px: Int): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }


    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }


    fun getActionBarHeight(activity: Activity): Int {
        val tv = TypedValue()
        var actionbarHeight= 0
        if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionbarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        }
        return actionbarHeight
    }
}
