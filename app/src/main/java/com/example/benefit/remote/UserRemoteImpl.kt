package com.example.benefit.remote

import android.graphics.Bitmap
import com.example.benefit.remote.models.*
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import splitties.experimental.ExperimentalSplittiesApi
import java.io.ByteArrayOutputStream
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */

@ExperimentalSplittiesApi
class UserRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val authorizedApiService: AuthorizedApiService
) : UserRemote {


    override suspend fun login(phoneNum: String): ResultWrapper<RespLogin> {
        return try {
            val response = apiService.login(phoneNum)
            ResultSuccess(response)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun signup(phoneNum: String): ResultWrapper<RegPhoneResp> {
        return try {
            val response = apiService.signup(phoneNum)
            if (response.msg == null) ResultSuccess(response)
            else ResultError(message = response.msg)
        } catch (e: HttpException) {
            ResultError(
                JSONObject(e.response()!!.errorBody()!!.string())["message"].toString(),
                e.code()
            )
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun loginSms(phoneNum: String, code: String): ResultWrapper<RespLoginSms> {
        return try {
            val response = apiService.loginsms(ReqLoginSms(phoneNum, code))
            if (response.msg == null) ResultSuccess(response)
            else ResultError(message = response.msg)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun loginCode(
        user_id: Int,
        user_token: String,
        phoneNum: String,
        device_code: String
    ): ResultWrapper<RespLoginCode> {
        return try {
            val response =
                apiService.logincode(ReqLoginCode(user_id, user_token, phoneNum, device_code))
            if (response.msg == null) ResultSuccess(response)
            else ResultError(message = response.msg)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun resendCode(phoneNum: String): ResultWrapper<String> {
        return try {
            val response = apiService.sendcode(phoneNum)
            if (response.isSuccessful) ResultSuccess("")
            else ResultError(
                JSONObject(response.errorBody()!!.string())["message"].toString(),
                response.code()
            )
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun checkcode(
        user_token: String,
        user_id: Int,
        phone_number: String,
        sms_code: String
    ): ResultWrapper<PlainResp> {
        return try {
            val response = apiService.checkcode(user_token, user_id, phone_number, sms_code)
            if (response.message == null) ResultSuccess(response)
            else ResultError(message = response.message)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun paymentCategories(): ResultWrapper<List<PaynetCategory>> {
        return try {
            val response = authorizedApiService.paymentCategories()
            ResultSuccess(response)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun getNews(page: Int, perPage: Int): ResultWrapper<List<NewsDTO>> {
        return try {
            val response = authorizedApiService.getNews(page, perPage)
            ResultSuccess(response)
        } catch (e: Exception) {
            ResultError(message = e.localizedMessage)
        }
    }

    override suspend fun termsAccept() = getFormattedResponse { authorizedApiService.termsAccept() }
    override suspend fun addPassportPhoto(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val file = stream.toByteArray().toRequestBody()
        val body = MultipartBody.Part.createFormData("image", "image.jpg", file)
        return getFormattedResponse { authorizedApiService.addPassportPhoto(order_card_id, body) }
    }

    override suspend fun addPhotoWithPassport(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val file = stream.toByteArray().toRequestBody()
        val body = MultipartBody.Part.createFormData("image", "image.jpg", file)
        return getFormattedResponse {
            authorizedApiService.addPhotoWithPassport(
                order_card_id,
                body
            )
        }
    }

    override suspend fun addWorkProof(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val map: MutableMap<String, RequestBody> = HashMap()
        map["order_card_id"] = order_card_id.toString().toRequestBody()
        map["user_id"] = AppPrefs.userId.toString().toRequestBody()
        map["user_auth"] = AppPrefs.userToken.toString().toRequestBody()

        val bos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos)

        if (bos.size() > 0) {
            val fileBody: RequestBody = bos.toByteArray().toRequestBody()
            map["image"] = fileBody
        }
        return getFormattedResponse { authorizedApiService.addWorkProof(map) }
    }

    override suspend fun addOrderCardAddress(order_card_id: Int, address: String) =
        getFormattedResponse { authorizedApiService.orderCardAddress(order_card_id, address) }

    override suspend fun addLimitSum(order_card_id: Int, sum: String) =
        getFormattedResponse { authorizedApiService.orderCardLimit(order_card_id, sum) }


    override suspend fun completeAddCard(order_card_id: Int) =
        getFormattedResponse { authorizedApiService.completeOrderCard(order_card_id) }

    override suspend fun getMyCards() = getFormattedResponse { authorizedApiService.getMyCards() }

    override suspend fun getMyReferralLink(): ResultWrapper<String> {
        val resp = getFormattedResponse { authorizedApiService.getMyReferralLink() }
        return when (resp) {
            is ResultError -> resp
            is ResultSuccess -> ResultSuccess(resp.value.referal_link_token)
        }.exhaustive
    }

}