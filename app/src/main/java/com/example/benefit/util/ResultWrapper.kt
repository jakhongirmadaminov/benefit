package com.example.benefit.util

import androidx.lifecycle.MutableLiveData
import com.example.benefit.remote.models.RespFormat
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

sealed class RequestState<out V> {
    object Loading : RequestState<Nothing>()
    data class Success<out V>(val value: V) : RequestState<V>()
    data class Error(val message: String? = null, val code: Int? = null) : RequestState<Nothing>()
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

suspend fun <T> getFormattedResponse(action: suspend () -> RespFormat<T>): ResultWrapper<T> {
    return try {
        val resp = action()
        when {
            resp.result?.data != null -> ResultSuccess(resp.result.data)
            resp.result?.error != null -> ResultError(resp.result.error.message)
            resp.error != null -> ResultError("", resp.error.status)
            else -> ResultError(resp.result?.message, resp.result?.error?.status)
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

suspend fun <T> makeRequest(
    resultState: MutableLiveData<RequestState<T>>,
    action: suspend () -> RespFormat<T>
) {
    try {
        resultState.value = RequestState.Loading
        val resp = action()
        resultState.value = when {
            resp.result?.data != null -> RequestState.Success(resp.result.data)
            resp.result?.error != null -> RequestState.Error(resp.result.error.message)
            resp.error != null -> RequestState.Error("", resp.error.status)
            else -> RequestState.Error(resp.result?.message, resp.result?.error?.status)
        }
    } catch (e: HttpException) {
        resultState.value = RequestState.Error(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        resultState.value = RequestState.Error(message = e.localizedMessage)
    }
}


suspend fun <T> getFormattedResponse(
    isLoading: MutableLiveData<Boolean>,
    action: suspend () -> RespFormat<T>
): ResultWrapper<T> {
    return try {
        isLoading.postValue(true)
        val resp = action()
        isLoading.postValue(false)
        when {
            resp.result?.data != null -> ResultSuccess(resp.result.data)
            resp.result?.error != null -> ResultError(resp.result.error.message)
            else -> ResultError(resp.result?.message)
        }
    } catch (e: HttpException) {
        isLoading.postValue(false)
        ResultError(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        isLoading.postValue(false)
        ResultError(message = e.localizedMessage)
    }
}



