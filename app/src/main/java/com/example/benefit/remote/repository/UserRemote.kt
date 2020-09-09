package com.example.benefit.remote.repository

import com.example.benefit.util.ResultWrapper


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a list of Bufferoos, from the cache
     */
    suspend fun login(phoneNum: String): ResultWrapper<String>
    suspend fun signup(phoneNum: String): ResultWrapper<String>
    suspend fun loginCode(phoneNum: String,code: String): ResultWrapper<String>
    suspend fun resendCode(phoneNum: String): ResultWrapper<String>
//    suspend fun loginSms(phoneNum: String, code: String): ResultWrapper<String>
//    suspend fun setPassword(token: String, phoneNum: String, password: String): ResultWrapper<String>
//    suspend fun editUserInfo(phoneNum: String): ResultWrapper<String>


//    //    suspend fun registerUser(user: User): ResultWrapper<String>
//    suspend fun confirmUser(user: UserCredentials): ResultWrapper<NUser>
//    suspend fun saveUserCity(cityId: Int): ResultWrapper<NUser>
//    suspend fun saveUser(user: User, image: Bitmap): ResultWrapper<NUser>
//    suspend fun updateProfileInfo(userInfo: UserInfo): ResultWrapper<UserInfo>
//    suspend fun updateAvatar(bitmap: Bitmap): ResultWrapper<NUser>
//    suspend fun addEducation(educationBody: EducationBody): ResultWrapper<NUser>
//    suspend fun addPassport(passport: Bitmap, passportRegistration: Bitmap): ResultWrapper<NUser>
//    suspend fun addDocFiles(files: List<Bitmap?>): ResultWrapper<NUser>
//    suspend fun addServicePrices(body: List<IdPriceBody>): ResultWrapper<ResponseBody>
//    suspend fun updateMyAreas(areas: List<Int>): ResultWrapper<NUser>
//    suspend fun updateSchedule(schedule: Map<String, List<Int>>): ResultWrapper<ResponseBody>
//    suspend fun getMyReviews(): ResultWrapper<DoctorReview>
//    suspend fun getProfileInfo(): ResultWrapper<NUser>
//    suspend fun deleteDoc(docId:Int): ResultWrapper<ResponseBody>
//    suspend fun getDocs(): ResultWrapper<NUser>
//    suspend fun getMySchedule(): ResultWrapper<NUser>
//    suspend fun getMyServicePrices(): ResultWrapper<NUser>
//    suspend fun getMyVisitAddresses(): ResultWrapper<NUser>
//    suspend fun getMyVisitPrices(): ResultWrapper<NUser>
//    suspend fun updateVisitPrices(body: ReqVisitPrices): ResultWrapper<ResponseBody>
//    suspend fun toggleStatus(): ResultWrapper<NUser>
}