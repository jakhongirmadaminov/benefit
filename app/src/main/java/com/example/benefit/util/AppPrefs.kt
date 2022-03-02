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
    var token by stringOrNullPref("token", null)

    @ExperimentalSplittiesApi
    var pin by stringOrNullPref("pin", null)

    @ExperimentalSplittiesApi
    var userToken by stringOrNullPref("userToken", null)

    @ExperimentalSplittiesApi
    var userId by intPref("userId", 0)

    @ExperimentalSplittiesApi
    var language by stringPref("language", "uz")

    @ExperimentalSplittiesApi
    var phoneNumber by stringOrNullPref("phoneNumber", null)

    @ExperimentalSplittiesApi
    var avatar by stringOrNullPref("avatar", null)

    @ExperimentalSplittiesApi
    var dobMillis by longPref("dobMillis", 0)

    @ExperimentalSplittiesApi
    var firstName by stringPref("firstName", "")

    @ExperimentalSplittiesApi
    var lastName by stringPref("lastName", "")

    @ExperimentalSplittiesApi
    var gender by stringPref("gender", EGender.MALE.name)

    @ExperimentalSplittiesApi
    var email by stringOrNullPref("email", null)


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