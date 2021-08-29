package com.example.benefit.util

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import com.example.benefit.R
import com.notkamui.keval.Keval
import kotlinx.android.synthetic.main.layout_calculator.view.*


class CalculatorLayout constructor(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {


    var edtSum: EditText? = null

    init {
        inflate(context, R.layout.layout_calculator, this)
        attachListeners()
        invalidate()
    }

    private fun attachListeners() {
        tvOne.setOnClickListener { edtSum?.append("1") }
        tvTwo.setOnClickListener { edtSum?.append("2") }
        tvThree.setOnClickListener { edtSum?.append("3") }
        tvFour.setOnClickListener { edtSum?.append("4") }
        tvFive.setOnClickListener { edtSum?.append("5") }
        tvSix.setOnClickListener { edtSum?.append("6") }
        tvSeven.setOnClickListener { edtSum?.append("7") }
        tvEight.setOnClickListener { edtSum?.append("8") }
        tvNine.setOnClickListener { edtSum?.append("9") }
        tvZero.setOnClickListener { edtSum?.append("0") }
        tvMinus.setOnClickListener { edtSum?.append(" - ") }
        tvPlus.setOnClickListener { edtSum?.append(" + ") }
        tvMultiply.setOnClickListener { edtSum?.append(" * ") }
        tvDivide.setOnClickListener { edtSum?.append(" / ") }
        tvEquals.setOnClickListener {
            edtSum?.let {
                if (it?.text.isNotBlank()) {
                    try {
                        it?.setText(Keval.eval(it?.text.toString()).toInt().toString())
                    } catch (e: Exception) {

                    }
                }
            }
        }

        backspace.setOnClickListener {
            edtSum?.let {
                if (it.text.isNotBlank()) {
                    it.setText(it.text.dropLast(1))
                }
            }
        }

    }


}