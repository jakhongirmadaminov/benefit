package uz.magnumactive.benefit.remote.repository

import android.graphics.Bitmap
import uz.magnumactive.benefit.remote.models.*
import uz.magnumactive.benefit.util.ResultWrapper
import java.math.BigInteger

interface UserRemote {

    suspend fun login(phoneNum: String): ResultWrapper<RespLogin>
    suspend fun signup(phoneNum: String, referal: String?): ResultWrapper<RegPhoneResp>
    suspend fun loginSms(phoneNum: String, code: String): ResultWrapper<RespLoginSms>
    suspend fun setPassword(
        password: String,
        phone_number: String,
        user_token: String,
        user_id: Int
    ): ResultWrapper<RespUserInfo>

    suspend fun loginCode(
        device_code: String
    ): ResultWrapper<RespLoginCode>

    suspend fun resendCode(phoneNum: String): ResultWrapper<String>
    suspend fun checkcode(
        user_token: String, user_id: Int, phone_number: String, sms_code: String
    ): ResultWrapper<RespUserInfo>

    suspend fun paymentCategories(): ResultWrapper<List<PaynetCategory>>

    suspend fun getNews(page: Int, perPage: Int = 10): ResultWrapper<List<NewsDTO>>

    suspend fun termsAccept(type_id: Int): ResultWrapper<RespAcceptTerms>
    suspend fun addPassportPhoto(order_card_id: Long, image: Bitmap): ResultWrapper<RespAcceptTerms>
    suspend fun addPhotoWithPassport(
        order_card_id: Long,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms>

    suspend fun addWorkProof(order_card_id: Long, image: Bitmap): ResultWrapper<RespAcceptTerms>
    suspend fun addOrderCardAddress(
        order_card_id: Long,
        address: String
    ): ResultWrapper<RespAcceptTerms>

    suspend fun addLimitSum(order_card_id: Long, sum: String): ResultWrapper<RespAcceptTerms>
    suspend fun completeAddCard(order_card_id: Long): ResultWrapper<RespAcceptTerms>
    suspend fun getMyCards(): ResultWrapper<MyCardsResp>
    suspend fun getMyReferralLink(): ResultWrapper<String>
    suspend fun uploadAvatar(bitmap: Bitmap): ResultWrapper<RespUserInfo>
    suspend fun updateUserInfo(
        name: String,
        lastName: String,
        gender: String,
        dobMillis: BigInteger
    ): ResultWrapper<RespUserInfo>

    suspend fun addNewCard(title: String, cardNumber: String, expiry: String): ResultWrapper<RespAddCard>

    suspend fun confirmNewCard(card_id: Long, sms_code: String): ResultWrapper<Any>

}