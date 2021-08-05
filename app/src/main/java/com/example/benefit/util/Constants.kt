package com.example.benefit.util

/**
 * Created by jahon on 06-May-20
 */
object Constants {

//    const val BASE_URL = "http://benefitbank.uz/"
    const val BASE_URL = "http://sys.benefitbank.uz/"

//    const val FILE_PROVIDER = "com.example.benefit.fileprovider"

    val MONTHS = hashMapOf(
        Pair(
            "ru", listOf(
                "Января",
                "Февраля",
                "Марта",
                "Апреля",
                "Мая",
                "Июня",
                "Июля",
                "Августа",
                "Сентября",
                "Октября",
                "Ноября",
                "Декабря"
            )
        ),
        Pair(
            "en", listOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "Ноября",
                "Декабря"
            )
        ),
        Pair(
            "uz", listOf(
                "Yanvar",
                "Fevral",
                "Mart",
                "Aprel",
                "May",
                "Iyun",
                "Iyul",
                "Avgust",
                "Sentabr",
                "Oktabr",
                "Noyabr",
                "Dekabr"
            )
        )
    )

    val WEEKDAYS = hashMapOf(
        Pair(
            "ru", listOf(
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота",
                "Воскресенье"
            )
        ),
        Pair(
            "uz", listOf(
                "Dushanba",
                "Seshanba",
                "Chorshanba",
                "Payshanba",
                "Juma",
                "Shanba",
                "Yakshanba"
            )
        ),
        Pair(
            "en", listOf(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
            )
        )
    )


    const val APP_DATABASE_NAME = "com.sablab.domvetdoctor.cache.db"
    const val APP_DATABASE_VERSION = 1


    const val ROLE_PASSENGER = "PASSANGER"
    const val TXT_IS_FROM_POST_PREVIEW = "IS_FROM_POST_PREVIEW"
    const val DRIVER_POST_SIMPLE = "DRIVER_SM"
    const val PASSENGER_POST_SIMPLE = "PASSANGER_SM"
    const val errPhoneFormat = -99
    const val TXT_CAR = "CAR"
    const val TXT_PASSENGER_POST = "PASSENGER_POST"
    const val FUEL_TYPE_PROPANE = "PROPANE"
    const val FUEL_TYPE_METHANE = "METHANE"
    const val FUEL_TYPE_PETROL = "PETROL"
    const val TXT_TOKEN = "TOKEN"
    const val TXT_ID = "ID"
    const val TXT_POSITION = "POSITION"
    const val TXT_PLACE = "PLACE"
    const val TXT_LANG = "LANGUAGE"
    const val TXT_FILTER = "FILTER"
    const val TXT_PAGE = "PAGE"
    const val RU = "ru"
    const val EN = "en"
    const val UZ = "uz"
    const val CODE_ADD_CAR = 99

}