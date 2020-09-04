package com.example.benefit.remote

import com.example.benefit.remote.repository.UserRemote
import com.example.benefit.util.ErrorWrapper
import com.example.benefit.util.ResultWrapper
import org.json.JSONObject
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */

class UserRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val authorizedApiService: AuthorizedApiService
) :
    UserRemote {



    override suspend fun login(phoneNum: String): ResultWrapper<String> {
        return try {
            val response = apiService.login(phoneNum)
            if (response.isSuccessful) ResultWrapper.Success("")
            else ErrorWrapper.ResponseError(
                response.code(),
                JSONObject(
                    response.errorBody()!!
                        .string()
                )["message"].toString()
            )
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

//    override suspend fun confirmUser(user: UserCredentials): ResultWrapper<NUser> {
//        return try {
//            val response = apiService.smsConfirm(user)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun saveUserCity(cityId: Int): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.saveUserCity(mapOf("city_id" to cityId.toString()))
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
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
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun updateProfileInfo(userInfo: UserInfo): ResultWrapper<UserInfo> {
//
//        return try {
//            val response = authorizedApiService.updateProfileInfo(/*"Bearer "+token,*/ userInfo)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getProfileInfo(): ResultWrapper<NUser> {
//
//        return try {
//            val response = authorizedApiService.getProfileInfo()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun deleteDoc(docId: Int): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.deleteDoc(docId)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getDocs(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getAllDocs()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getMySchedule(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMySchedule()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getMyServicePrices(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyServicePrices()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getMyVisitAddresses(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyVisitAddresses()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getMyVisitPrices(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.getMyVisitPrices()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun updateVisitPrices(body: ReqVisitPrices): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.updateVisitPrices(body)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun toggleStatus(): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.toggleStatus()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
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
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun getMyReviews(): ResultWrapper<DoctorReview> {
//        return try {
//            val response = authorizedApiService.getMyReviews()
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
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
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun addEducation(educationBody: EducationBody): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.addEducation(educationBody)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
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
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
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
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun addServicePrices(body: List<IdPriceBody>): ResultWrapper<ResponseBody> {
//        return try {
//            val response = authorizedApiService.addServicePrices(body)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }
//
//    override suspend fun updateMyAreas(areas: List<Int>): ResultWrapper<NUser> {
//        return try {
//            val response = authorizedApiService.updateMyAreas(areas)
//            if (response.isSuccessful) ResultWrapper.Success(response.body()!!)
//            else ErrorWrapper.ResponseError(response.code(),
//                                            JSONObject(response.errorBody()!!
//                                                           .string())["message"].toString())
//        } catch (e: Exception) {
//            ErrorWrapper.SystemError(e)
//        }
//    }

}