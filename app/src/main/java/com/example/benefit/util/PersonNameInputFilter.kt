package com.example.benefit.util

import android.text.InputFilter

/**
 * Created by jahon on 23-Aug-20
 */


object PersonNameInputFilter{


    fun personNameInputFilters() : Array<InputFilter?>{
        val filters = arrayOfNulls<InputFilter>(1)
        filters[0] = InputFilter { source, start, end, dest, dstart, dend ->
            if (end > start) {
                val acceptedChars = charArrayOf(',', ';', '.', '?', '!', ':', '"')
                for (index in start until end) {
                    if (String(acceptedChars).contains(source[index].toString())) {
                        return@InputFilter ""
                    }
                }
            }
            null
        }
        return filters
    }

    fun emailInputFilters() : Array<InputFilter?>{
        val filters = arrayOfNulls<InputFilter>(1)
        filters[0] = InputFilter { source, start, end, dest, dstart, dend ->
            if (end > start) {
                val acceptedChars = charArrayOf(',', ';', '?', '!', ':', '"')
                for (index in start until end) {
                    if (String(acceptedChars).contains(source[index].toString())) {
                        return@InputFilter ""
                    }
                }
            }
            null
        }
        return filters
    }
}