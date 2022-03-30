package uz.magnumactive.benefit.remote

import android.util.Log
import uz.magnumactive.benefit.BuildConfig.DEBUG
import uz.magnumactive.benefit.util.AppPrefs
import okhttp3.Interceptor
import okhttp3.Response
import splitties.experimental.ExperimentalSplittiesApi

/**
 * Interceptor to add auth token to requests
 */
@ExperimentalSplittiesApi
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (AppPrefs.token.isNullOrBlank()) throw Exception()
        requestBuilder.addHeader("Authorization", "Bearer ${AppPrefs.token}")
        if (DEBUG) Log.d("TOKEEEEN", AppPrefs.token!!)
        return chain.proceed(requestBuilder.build())
    }
}