package com.example.benefit.util

import com.example.benefit.remote.models.RespFormatter
import org.json.JSONObject
import retrofit2.HttpException

sealed class ResultWrapper<out V> {
    override fun toString(): String {
        return when (this) {
            is ResultSuccess<*> -> "Success [data = ${this.value}]"
            is ResultError -> "Error [exception=${this.message}]"
        }.exhaustive
    }
}

data class ResultError(val message: String? = null, val code: Int? = null) :
    ResultWrapper<Nothing>()

data class ResultSuccess<out V>(val value: V) : ResultWrapper<V>()

suspend fun <T> getParsedResponse(action: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultSuccess(action())
    } catch (e: HttpException) {
        ResultError(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        ResultError(message = e.localizedMessage)
    }
}

suspend fun <T> getFormattedResponse(action: suspend () -> RespFormatter<T>): ResultWrapper<T> {
    return try {
        val resp = action()
        when {
            resp.result?.data != null -> ResultSuccess(resp.result.data)
            resp.result?.error != null -> ResultError(resp.result.error.message)
            else -> ResultError(resp.result?.message)
        }
    } catch (e: HttpException) {
        ResultError(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        ResultError(message = e.localizedMessage)
    }
}



