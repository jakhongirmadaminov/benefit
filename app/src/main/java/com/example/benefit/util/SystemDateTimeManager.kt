package com.example.benefit.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class SystemDateTimeManager : DateTimeManager {
    private val DATE_TIME_FORMAT_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        }
    }

    private val RECEIPT_DATE_TIME_FORMAT_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        }
    }

    private val RECEIPT_DATE_FORMAT_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        }
    }

    private val RECEIPT_DATE_TIME_FORMAT_LONG_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("EEEE dd/MM/yyyy HH:mm", Locale.getDefault())
        }
    }

    private val TRANSACTION_HISTORY_LIST_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        }
    }

    private val DATE_TIME_TIMEZONE_FORMAT_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        }
    }

    private val DATE_FORMAT_BANK_DATE = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        }
    }

    private val TIME_FORMAT_BANK_TIME = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("HHmmss", Locale.getDefault())
        }
    }

    private val TIME_FORMAT_BANK_DATE_TIME = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("ddMMyyyy HHmmss", Locale.getDefault())
        }
    }

    private val DATE_FORMAT_SHORT_DATE = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("ddMMMyy", Locale.getDefault())
        }
    }

    private val TIME_FORMAT_SHORT_TIME = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("HH:mm", Locale.getDefault())
        }
    }

    private val TIME_FORMAT_SIMPLE_TIME = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        }
    }

    private val SETTLEMENT_DATE_FORMAT = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        }
    }

    private fun getDateFormat(type: DateFormatType): SimpleDateFormat {
        return when (type) {
            DateFormatType.DATE_TIME -> DATE_TIME_FORMAT_SDF.get()
            DateFormatType.DATE_TIME_TIMEZONE -> DATE_TIME_TIMEZONE_FORMAT_SDF.get()
            DateFormatType.BANK_DATE_TIME -> TIME_FORMAT_BANK_DATE_TIME.get()
            DateFormatType.BANK_DATE -> DATE_FORMAT_BANK_DATE.get()
            DateFormatType.BANK_TIME -> TIME_FORMAT_BANK_TIME.get()
            DateFormatType.RECEIPT_DATE_TIME -> RECEIPT_DATE_TIME_FORMAT_SDF.get()
            DateFormatType.RECEIPT_DATE_TIME_LONG -> RECEIPT_DATE_TIME_FORMAT_LONG_SDF.get()
            DateFormatType.RECEIPT_DATE -> RECEIPT_DATE_FORMAT_SDF.get()
            DateFormatType.SHORT_DATE -> DATE_FORMAT_SHORT_DATE.get()
            DateFormatType.SHORT_TIME -> TIME_FORMAT_SHORT_TIME.get()
            DateFormatType.TIME -> TIME_FORMAT_SIMPLE_TIME.get()
            DateFormatType.SETTLEMENT_DATE -> SETTLEMENT_DATE_FORMAT.get()
            DateFormatType.CERTIFICATE_DATE_TIME -> RECEIPT_DATE_TIME_FORMAT_SDF.get()
            DateFormatType.TRANSACTION_HISTORY_LIST -> TRANSACTION_HISTORY_LIST_SDF.get()
        } ?: throw IllegalStateException("Can't get date formatter for thread : $type")
    }

    override fun timestamp(): String {
        val now = System.currentTimeMillis()
        return DATE_TIME_FORMAT_SDF.get()?.format(Date(now))
            ?: throw IllegalStateException("Can't get a SimpleDateFormat instance for this thread.")
    }

    override fun convert(date: Date, toType: DateFormatType): String {
        return getDateFormat(toType).format(date)
    }



    override fun convert(
        value: String,
        fromType: DateFormatType,
        toType: DateFormatType
    ): String? {
        val fromFormat = getDateFormat(fromType)
        val toFormat = getDateFormat(toType)

        return try {
            fromFormat.parse(value)?.let {
                toFormat.format(it)
            }
        } catch (pe: ParseException) {
            null
        }
    }
}
