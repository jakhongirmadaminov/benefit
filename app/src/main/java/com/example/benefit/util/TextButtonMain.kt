package com.example.benefit.util

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.example.benefit.R


class TextButtonMain constructor(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(
        ContextThemeWrapper(
            context,
            R.style.TextButtonMainStyle
        ), attrs, R.style.TextButtonMainStyle
    ) {


    init {
//        background = ContextCompat.getDrawable(context, R.drawable.selector_btn_main)
//        setTextAppearance(context, R.style.ButtonTextAppaerance)
        setTextColor(isEnabled)
//        val a = context.obtainStyledAttributes(attrs, R.styleable.ButtonMain)
//        val N = a.indexCount
//        for (i in 0 until N) {
//            val attr = a.getIndex(i)
//            when (attr) {
//                R.styleable.ButtonMain_myEnabled -> {
//                    isEnabled = a.getBoolean(attr, false)
//                }
//            }
//        }
//        a.recycle()

    }

    private fun setTextColor(enabled: Boolean) {
        setTextColor(
            if (enabled) Color.BLACK else ContextCompat.getColor(
                context,
                R.color.textlightGrey
            )
        )
    }


    fun myEnabled(enabled: Boolean) {
        isEnabled = enabled
        setTextColor(enabled)
    }


}