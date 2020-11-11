package com.example.benefit.util

import com.example.benefit.remote.models.RespFormatter
import org.json.JSONObject
import retrofit2.HttpException

sealed class ResultWrapper<out V> {
    override fun toString(): String {
        return when (this) {
            is ResultSuccess<*> -> "Success [data = ${this.value}]"
            is Error -> "Error [exception=${this.localizedMessage}]"
//            InProgress -> "Loading"
            else -> ""
        }
    }
}

//object InProgress : ResultWrapper<Nothing>()
data class ResultError(val message: String? = null, val code: Int? = null) :
    ResultWrapper<Nothing>()

data class ResultSuccess<out V>(val value: V) : ResultWrapper<V>()

suspend fun <T> getParsedResponse(action: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultSuccess(action())
//            else ResultError(message = action.result)
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
        if (resp.result != null) {
            ResultSuccess(resp.result)
        } else if (resp.error != null) ResultError(resp.error.message)
        else ResultError(resp.message)
    } catch (e: HttpException) {
        ResultError(
            JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
            e.code()
        )
    } catch (e: Exception) {
        ResultError(message = e.localizedMessage)
    }
}


//sealed class ErrorWrapper : ResultWrapper<Nothing>() {
//    data class ResponseError(val code: Int? = null, val message: String? = null) : ErrorWrapper()
//    data class SystemError(val err: Throwable) : ErrorWrapper()
//}

//    data class Error<out E>(val error: E) : ResultWrapper<E, Nothing>()
//    data class NetworkError<out E>(val error: E) : ResultWrapper<E, Nothing>()

//    companion object Factory {
//        inline fun <V> build(function: () -> V): ResultWrapper< V> = try {
//            Success(function.invoke())
//        } catch (e: Exception) {
//            Error(e)
//        }
//    }


/*  return withContext(dispatcher) {
      try {
          ResultWrapper.Success(apiCall.invoke())
      } catch (throwable: Throwable) {
          when (throwable) {
              is IOException -> ResultWrapper.NetworkError
              is HttpException -> {
                  val code = throwable.code()
                  val errorResponse = convertErrorBody(throwable)
                  ResultWrapper.GenericError(code, errorResponse)
              }
              else -> {
                  ResultWrapper.GenericError(null, null)
              }
          }
      }
  }*/




