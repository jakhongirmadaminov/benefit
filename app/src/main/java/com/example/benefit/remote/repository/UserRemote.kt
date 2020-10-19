package com.example.benefit.remote.repository

import com.example.benefit.remote.models.PlainResp
import com.example.benefit.remote.models.RegPhoneResp
import com.example.benefit.util.ResultWrapper

interface UserRemote {

    suspend fun login(phoneNum: String): ResultWrapper<String>
    suspend fun signup(phoneNum: String): ResultWrapper<RegPhoneResp>
    suspend fun loginCode(phoneNum: String, code: String): ResultWrapper<String>
    suspend fun resendCode(phoneNum: String): ResultWrapper<String>
    suspend fun checkcode(
        user_token: String, user_id: Int, phone_number: String, sms_code: String
    ): ResultWrapper<PlainResp>


}