package com.example.benefit.util

/**
 * Created by jahon on 23-Apr-20
 */


import com.example.benefit.ui.auth.registration.EGender
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences
import splitties.preferences.edit

@ExperimentalSplittiesApi
object AppPrefs : Preferences("myPrefs") {

    @ExperimentalSplittiesApi
    var token by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var pin by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var userToken by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var userId by intPref(0)

    @ExperimentalSplittiesApi
    var language by stringPref("uz")

    @ExperimentalSplittiesApi
    var phoneNumber by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var avatar by stringOrNullPref(null)

    @ExperimentalSplittiesApi
    var dobMillis by longPref(0)

    @ExperimentalSplittiesApi
    var firstName by stringPref("")

    @ExperimentalSplittiesApi
    var lastName by stringPref("")

    @ExperimentalSplittiesApi
    var gender by stringPref(EGender.MALE.name)

    @ExperimentalSplittiesApi
    var email by stringOrNullPref(null)


    fun isLoggedIn(): Boolean {
        return !userToken.isNullOrBlank()
    }

    fun logOut() {
        edit {
            userToken = null
            token = null
        }
    }
}