package com.example.benefit.remote.repository

import com.example.benefit.util.ResultWrapper

interface UserRemote {

    suspend fun login(phoneNum: String): ResultWrapper<String>
    suspend fun signup(phoneNum: String): ResultWrapper<String>
    suspend fun loginCode(phoneNum: String, code: String): ResultWrapper<String>
    suspend fun resendCode(phoneNum: String): ResultWrapper<String>


}