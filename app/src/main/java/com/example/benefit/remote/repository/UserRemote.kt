package com.example.benefit.remote.repository

import android.graphics.Bitmap
import com.example.benefit.remote.models.*
import com.example.benefit.util.ResultWrapper

interface UserRemote {

    suspend fun login(phoneNum: String): ResultWrapper<RespLogin>
    suspend fun signup(phoneNum: String): ResultWrapper<RegPhoneResp>
    suspend fun loginSms(phoneNum: String, code: String): ResultWrapper<RespLoginSms>
    suspend fun loginCode(
        user_id: Int,
        user_token: String,
        phoneNum: String,
        device_code: String
    ): ResultWrapper<RespLoginCode>

    suspend fun resendCode(phoneNum: String): ResultWrapper<String>
    suspend fun checkcode(
        user_token: String, user_id: Int, phone_number: String, sms_code: String
    ): ResultWrapper<PlainResp>

    suspend fun paymentCategories(): ResultWrapper<List<PaynetCategory>>

    suspend fun getNews(page: Int, perPage: Int = 10): ResultWrapper<List<NewsDTO>>

    suspend fun termsAccept(): ResultWrapper<RespAcceptTerms>
    suspend fun addPassportPhoto(order_card_id: Int, image: Bitmap): ResultWrapper<RespAcceptTerms>
    suspend fun addPhotoWithPassport(order_card_id: Int, image: Bitmap): ResultWrapper<RespAcceptTerms>
    suspend fun addWorkProof(order_card_id: Int, image: Bitmap): ResultWrapper<RespAcceptTerms>
    suspend fun addOrderCardAddress(order_card_id: Int, address: String): ResultWrapper<RespAcceptTerms>
    suspend fun addLimitSum(order_card_id: Int, sum: String): ResultWrapper<RespAcceptTerms>
    suspend fun completeAddCard(order_card_id: Int): ResultWrapper<RespAcceptTerms>
    suspend fun getMyCards(): ResultWrapper<List<CardDTO>>
    suspend fun getMyReferralLink(): ResultWrapper<String>

}