package uz.magnumactive.benefit.util

import java.util.*

enum class DateFormatType {
    DATE_TIME,
    DATE_TIME_TIMEZONE,
    BANK_DATE,
    BANK_TIME,
    BANK_DATE_TIME,
    RECEIPT_DATE_TIME,
    RECEIPT_DATE_TIME_LONG,
    RECEIPT_DATE,
    SHORT_DATE,
    SHORT_TIME,
    TIME,
    SETTLEMENT_DATE,
    CERTIFICATE_DATE_TIME,
    TRANSACTION_HISTORY_LIST
}

interface DateTimeManager {
    fun timestamp(): String
    fun convert(date: Date, toType: DateFormatType): String

    fun convert(value: String, fromType: DateFormatType, toType: DateFormatType): String?
}
