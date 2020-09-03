package com.example.benefit.util

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    @JvmField
    val FORMAT_AS_NUMBER: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyyMMdd", Locale.forLanguageTag("ru"))
            }
        }
    val FORMAT_AS_DATE: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("ru"))
            }
        }

    @JvmField
    val FORMAT_AS_CHOOSEN_DATE: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-mm-dd", Locale.forLanguageTag("ru"))
            }
        }
    val FORMAT_AS_DATETIME: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.forLanguageTag("ru"))
            }
        }
    val YYYYMMDDHHMMSS: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyyMMddHHmmss", Locale.forLanguageTag("ru"))
            }
        }

    val YYYY_MM_DD_HHMMSS: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"))
            }
        }

    val HH_MM: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("HH:mm", Locale.forLanguageTag("ru"))
            }
        }
    val FORMAT_AS_WEEK_DATE: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("EEEE, dd.MM.yyyy", Locale.forLanguageTag("ru"))
            }
        }

    fun parse(s: String): Date? {
        return if (TextUtils.isEmpty(s)) {
            null
        } else try {
            when (s.length) {
                8 -> FORMAT_AS_NUMBER.get()!!.parse(s)
                10 -> FORMAT_AS_DATE.get()!!.parse(s)
                14 -> YYYYMMDDHHMMSS.get()!!.parse(s)
                19 -> YYYY_MM_DD_HHMMSS.get()!!.parse(s)
                else -> FORMAT_AS_DATETIME.get()!!.parse(s)
            }
        } catch (e: ParseException) {
            throw IllegalArgumentException("Date time format error:$s")
        }
    }

    @JvmStatic
    fun format(date: Date?, fmt: SimpleDateFormat?): String {
        return try {
            fmt!!.format(date)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun format(
        date: Date?,
        fmt: ThreadLocal<SimpleDateFormat?>
    ): String {
        return format(date, fmt.get())
    }

    fun convert(s: String, fmt: SimpleDateFormat?): String {
        return format(parse(s), fmt)
    }

    @JvmStatic
    fun convert(
        s: String,
        fmt: ThreadLocal<SimpleDateFormat?>
    ): String {
        return convert(s, fmt.get())
    }
}