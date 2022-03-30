package uz.magnumactive.benefit.util

/**
 * Created by jahon on 28-Aug-20
 */
object RussianDateSuffixUtil {

    fun getYearSuffixRU(year: Int): String {
        return if (year == 0) "" else if (year % 10 in 5..9 || year % 10 == 0 || year in 12..20) {
            "$year лет"
        } else if (year % 10 in 2..4) {
            "$year года"
        } else {
            "$year год"
        }
    }

    fun getMonthSuffixRU(month: Int): String {
        return when (month) {
            0 -> ""
            1 -> "$month месяц"
            in 2..4 -> "$month месяца"
            else -> "$month месяцев"
        }
    }

    fun getDaySuffixRU(day: Int): String {
        return if (day == 0) "" else if (day!=11 && day % 10 == 1) {
            "$day день"
        } else if (day % 10 in 2..4) {
            "$day дня"
        } else {
            "$day дней"
        }
    }
    fun getHourSuffixRU(hour: Int): String {
        return if (hour == 0) "" else if (hour!=11 && hour % 10 == 1) {
            "$hour час"
        } else if (hour % 10 in 2..4) {
            "$hour часа"
        } else {
            "$hour часов"
        }
    }

}