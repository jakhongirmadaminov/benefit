package com.example.benefit.remote

import android.graphics.Bitmap
import com.example.benefit.remote.models.*
import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.ResultWrapper
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
        return getFormattedResponse { authorizedApiService.addPassportPhoto(order_card_id /*body*/) }
    }

    override suspend fun addPhotoWithPassport(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val file = stream.toByteArray().toRequestBody()
        val body = MultipartBody.Part.createFormData("image", "image.jpg", file)
        return getFormattedResponse { authorizedApiService.addPhotoWithPassport(order_card_id /*body*/) }
    }

    override suspend fun addWorkProof(
        order_card_id: Int,
        image: Bitmap
    ): ResultWrapper<RespAcceptTerms> {
        val map: MutableMap<String, RequestBody> = HashMap()
        map["order_card_id"] = order_card_id.toString().toRequestBody()
        map["user_id"] = AppPrefs.userId.toString().toRequestBody()
        map["user_auth"] =AppPrefs.userToken.toString().toRequestBody()

        val bos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos)

        if (bos.size() > 0) {
            val fileBody: RequestBody = bos.toByteArray().toRequestBody()
            map["image"] = fileBody
        }
//        val body = MultipartBody.Part.createFormData("image", "image.jpg", file)
        return getFormattedResponse { authorizedApiService.addWorkProof(map/*, body*/) }
    }

    override suspend fun addOrderCardAddress(
        order_card_id: Int,
        address: String
    ): ResultWrapper<RespAcceptTerms> {
        return getFormattedResponse {
            authorizedApiService.orderCardAddress(
                order_card_id,
                address
            )
        }
    }

    override suspend fun addLimitSum(
        order_card_id: Int,
        sum: String
    ): ResultWrapper<RespAcceptTerms> {
        return getFormattedResponse { authorizedApiService.orderCardLimit(order_card_id, sum) }
    }

    override suspend fun completeAddCard(
        order_card_id: Int
    ): ResultWrapper<RespAcceptTerms> {
        return getFormattedResponse { authorizedApiService.completeOrderCard(order_card_id) }
    }

    private suspend fun <T> getFormattedResponse(action: suspend () -> T): ResultWrapper<T> {
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


//    override suspend fun confirmUser(user: UserCredentials): ResultWrapper<NUser> {
//        return try {
//            val response = apiService.smsConfirm(user)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun saveUserCity(cityId: Int): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.saveUserCity(mapOf("city_id" to cityId.toString()))
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun saveUser(user: User, image: Bitmap): ResultWrapper<NUser> {
//        return try {
//
//
//            val map: MutableMap<String, RequestBody> = HashMap()
//            if (user.email!!.isNotEmpty())
//                map["email"] = user.email!!.toRequestBody()
//            if (user.firstName!!.isNotEmpty())
//                map["first_name"] = user.firstName!!.toRequestBody()
//            if (user.middleName!!.isNotEmpty())
//                map["middle_name"] = user.middleName!!.toRequestBody()
//            if (user.lastName!!.isNotEmpty())
//                map["last_name"] = user.lastName!!.toRequestBody()
//
//            map["city_id"] = user.cityId!!.toRequestBody()
//
//            val bos = ByteArrayOutputStream()
//            image.compress(Bitmap.CompressFormat.JPEG, 100, bos)
//
//            if (bos.size() > 0) {
//                val fileBody: RequestBody = bos.toByteArray().toRequestBody()
//                map["avatar\"; filename=\"pp.JPG\""] = fileBody
//            }
//
//            val response = authorizedApiService.saveUser(map)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun updateProfileInfo(userInfo: UserInfo): ResultWrapper<UserInfo> {
//
//        return try {
//            val response = authorizedApiService.updateProfileInfo(/*"Bearer "+token,*/ userInfo)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getProfileInfo(): ResultWrapper<NUser> {
//
//        return try {
//            val response = authorizedApiService.getProfileInfo()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun deleteDoc(docId: Int): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.deleteDoc(docId)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getDocs(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getAllDocs()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getMySchedule(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMySchedule()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getMyServicePrices(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyServicePrices()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getMyVisitAddresses(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyVisitAddresses()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getMyVisitPrices(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyVisitPrices()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun updateVisitPrices(body: ReqVisitPrices): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.updateVisitPrices(body)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun toggleStatus(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.toggleStatus()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun updateSchedule(schedule: Map<String, List<Int>>): ResultWrapper<ResponseBody> {
//        return try {
//
//            val body = MyScheduleBody(schedule["monday"],
//                                      schedule["tuesday"],
//                                      schedule["wednesday"],
//                                      schedule["thursday"],
//                                      schedule["friday"],
//                                      schedule["saturday"],
//                                      schedule["sunday"])
//
//            val response =
//                authorizedApiService.updateSchedule(/*"Bearer "+token,*/   body)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun getMyReviews(): ResultWrapper<DoctorReview> {
//        return try {
//            val response = authorizedApiService.getMyReviews()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun updateAvatar(bitmap: Bitmap): ResultWrapper<NUser> {
//        return try {
//
//            val map: MutableMap<String, RequestBody> = HashMap()
//            val bos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
//
//            if (bos.size() > 0) {
//                val fileBody: RequestBody = bos.toByteArray().toRequestBody()
//                map["avatar\"; filename=\"pp.JPG\""] = fileBody
//            }
//
//            val response = authorizedApiService.updateUserAvatar(map)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun addEducation(educationBody: EducationBody): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.addEducation(educationBody)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun addPassport(passport: Bitmap,
//                                     passportRegistration: Bitmap): ResultWrapper<NUser> {
//        return try {
//
//            val map: MutableMap<String, RequestBody> = HashMap()
//            val bos = ByteArrayOutputStream()
//            passport.compress(Bitmap.CompressFormat.JPEG, 100, bos)
//
//            if (bos.size() > 0) {
//                val fileBody: RequestBody = bos.toByteArray().toRequestBody()
//                map["passport\"; filename=\"pp.JPG\""] = fileBody
//            }
//
//            val bos2 = ByteArrayOutputStream()
//            passportRegistration.compress(Bitmap.CompressFormat.JPEG, 100, bos2)
//
//            if (bos2.size() > 0) {
//                val fileBody: RequestBody = bos2.toByteArray().toRequestBody()
//                map["passport_registration\"; filename=\"pp.JPG\""] = fileBody
//            }
//
//            val response = authorizedApiService.addPassport(map)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun addDocFiles(files: List<Bitmap?>): ResultWrapper<NUser> {
//        return try {
//            val map: LinkedHashMap<String, RequestBody> = LinkedHashMap()
//
//            files.forEachIndexed { index, bitmap ->
//                val bos = ByteArrayOutputStream()
//                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
//
//                if (bos.size() > 0) {
//                    val fileBody: RequestBody = bos.toByteArray().toRequestBody()
//                    map["file[$index]\"; filename=\"pp.JPG\""] = fileBody
//                }
//            }
//
//            val response = authorizedApiService.addDocFiles(map)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun addServicePrices(body: List<IdPriceBody>): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.addServicePrices(body)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }
//
//    override suspend fun updateMyAreas(areas: List<Int>): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.updateMyAreas(areas)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ResultError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ResultError(message = e.localizedMessage)
//        }
//    }

}