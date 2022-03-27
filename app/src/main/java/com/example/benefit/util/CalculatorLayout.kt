package com.example.benefit.util

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.benefit.R
import com.notkamui.keval.Keval
import kotlinx.android.synthetic.main.layout_calculator.view.*


class CalculatorLayout constructor(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    var amount = 0
    var minAmount = 1000

    var footerTextView: TextView? = null
    var edtSum: EditText? = null
        set(value) {
            field = value
            edtSum?.doOnTextChanged { text, start, before, count ->
                text?.let {
                    footerTextView?.apply {
                        amount = if (it.indexOfAny(charArrayOf('*', '/', '-', '+')) >= 0) {
                            try {
                                Keval.eval(text.toString()).toInt()
                            } catch (e: Exception) {
                                0
                            }
                        } else {
                            if (text.isNullOrBlank()) 0 else text.toString().trim().toInt()
                        }
                        if (amount < minAmount) {
                            amount = 0
                            setTextColor(ContextCompat.getColor(context, R.color.error_red))
                            setText(
                                context.getString(
                                    R.string.minimum_sum_is,
                                    minAmount.toString()
                                )
                            )
                        } else {
                            setTextColor(ContextCompat.getColor(context, R.color.textlightGrey))
                            setText(
                                context.getString(R.string.comission) + ": " + "0.5%" + "\n" +
                                        context.getString(R.string.total) + ": " + (amount + amount * 0.005).toString()
                            )
                        }
                    }
                } ?: run {
                    footerTextView?.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.textlightGrey))
                        setText(
                            context.getString(R.string.comission) + ": " + "0.5%" + "\n" +
                                    context.getString(R.string.total) + ": " + (amount + amount * 0.005).toString()
                        )
                    }
                    amount = 0
                }
            }
        }

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
        tvZero.setOnClickListener { if (!edtSum?.text.isNullOrBlank()) edtSum?.append("0") }
        tvMinus.setOnClickListener { edtSum?.append(" - ") }
        tvPlus.setOnClickListener { edtSum?.append(" + ") }
        tvMultiply.setOnClickListener { edtSum?.append(" * ") }
        tvDivide.setOnClickListener { edtSum?.append(" / ") }
        tvEquals.setOnClickListener {
            edtSum?.let {
                if (it.text.isNotBlank()) {
                    try {
                        it.setText(Keval.eval(it.text.toString()).toInt().toString())
                    } catch (e: Exception) {

                    }
                }
            }
        }

        backspace.setOnClickListener {
            edtSum?.let {
                if (!it.text.isNullOrBlank()) {
                    val amount = it.text.dropLast(1)
                    it.setText(amount)
                }
            }
        }

    }


}