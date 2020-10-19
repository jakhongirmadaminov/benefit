package com.example.benefit.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi
object AppPrefs : Preferences("myPrefs") {

    @ExperimentalSplittiesApi
    var token by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var language by stringPref("uz")

    @ExperimentalSplittiesApi
    var phoneNumber by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var avatar by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var birth_date by stringPref("")

    @ExperimentalSplittiesApi
    var firstName by stringPref("")

    @ExperimentalSplittiesApi
    var middleName by stringPref("")

    @ExperimentalSplittiesApi
    var lastName by stringPref("")

    @ExperimentalSplittiesApi
    var email by stringOrNullPref(null)

}