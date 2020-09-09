package com.example.benefit.util

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.example.benefit.R


class ButtonMain constructor(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatButton(ContextThemeWrapper(context, R.style.ButtonMainStyle), attrs, R.style.ButtonMainStyle) {


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
            if (enabled) Color.WHITE else ContextCompat.getColor(
                context,
                R.color.grey_text
            )
        )
    }


    fun myEnabled(enabled: Boolean) {
        isEnabled = enabled
        setTextColor(enabled)
    }


}