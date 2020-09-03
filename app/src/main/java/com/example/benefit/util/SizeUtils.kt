package com.example.benefit.util

import android.content.Context
import android.util.DisplayMetrics


object SizeUtils {

    fun dpToPx(context: Context, dp: Int): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun pxToDp(context: Context, px: Int): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}
