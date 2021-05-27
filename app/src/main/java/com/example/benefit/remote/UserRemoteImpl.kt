package com.example.benefit.remote

import android.graphics.Bitmap
import com.example.benefit.remote.models.*
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import splitties.experimental.ExperimentalSplittiesApi
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */

@ExperimentalSplittiesApi
class UserRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val authApiService: AuthApiService
) : UserRemote {


    override suspend fun login(phoneNum: String) =
        getFormattedResponse { apiService.login(phoneNum) }

    override suspend fun signup(phoneNum: String, referal: String?) =
        getFormattedResponse { apiService.signup(phoneNum, referal) }

    override suspend fun loginSms(phoneNum: String, code: String) =
        getFormattedResponse { apiService.loginsms(ReqLoginSms(phoneNum, code)) }

    override suspend fun setPassword(
        password: String,
        phone_number: String,
        user_token: String,
        user_id: Int
    ) = getFormattedResponse { apiService.setPassword(phone_number, password, user_token, user_id) }

    override suspend fun loginCode(device_code: String) =
        getFormattedResponse { apiService.logincode(ReqLoginCode(device_code)) }

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
    ) = getFormattedResponse { apiService.checkcode(user_token, user_id, phone_number, sms_code) }

    override suspend fun paymentCategories() =
        getFormattedResponse { authApiService.paymentCategories() }

    override suspend fun getNews(page: Int, perPage: Int) =
        getFormattedResponse { authApiService.getNews(page, perPage) }

    override suspend fun termsAccept(type_id: Int) =
        getFormattedResponse { authApiService.termsAccept(type_id) }

    override suspend fun addPassportPhoto(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val file = stream.toByteArray().toRequestBody()
        val body = MultipartBody.Part.createFormData("image", "image.jpg", file)
        return getFormattedResponse { authApiService.addPassportPhoto(order_card_id, body) }
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
            authApiService.addPhotoWithPassport(
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
        return getFormattedResponse { authApiService.addWorkProof(map) }
    }

    override suspend fun uploadAvatar(bitmap: Bitmap): ResultWrapper<RespUserInfo> {
        val map: MutableMap<String, RequestBody> = HashMap()
        map["user_id"] = AppPrefs.userId.toString().toRequestBody()
        map["user_token"] = AppPrefs.userToken.toString().toRequestBody()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)

        if (bos.size() > 0) {
            val fileBody: RequestBody = bos.toByteArray().toRequestBody()
            map["avatar"] = fileBody
        }
        return getFormattedResponse { authApiService.uploadAvatar(map) }
    }

    override suspend fun updateUserInfo(
        name: String,
        lastName: String,
        gender: String,
        dobMillis: BigInteger
    ) = getFormattedResponse {
        authApiService.updateUserInfo(ReqUserInfo(name, lastName, gender, dobMillis))
    }

    override suspend fun addNewCard(
        title: String,
        cardNumber: String,
        expiry: Int
    ) = getFormattedResponse {
        authApiService.addNewCard(
            ReqCard(
                cardNumber,
                title,
                (expiry.toString().substring(2) + expiry.toString().substring(0, 2)).toInt()
            )
        )
    }

    override suspend fun addOrderCardAddress(order_card_id: Int, address: String) =
        getFormattedResponse { authApiService.orderCardAddress(order_card_id, address) }

    override suspend fun addLimitSum(order_card_id: Int, sum: String) =
        getFormattedResponse { authApiService.orderCardLimit(order_card_id, sum) }


    override suspend fun completeAddCard(order_card_id: Int) =
        getFormattedResponse { authApiService.completeOrderCard(order_card_id) }

    override suspend fun getMyCards() = getFormattedResponse { authApiService.getMyCards() }

    override suspend fun getMyReferralLink(): ResultWrapper<String> {
        val resp = getParsedResponse { authApiService.getMyReferralLink() }
        return when (resp) {
            is ResultError -> resp
            is ResultSuccess -> ResultSuccess(resp.value.referal_link_token)
        }.exhaustive
    }

}