package com.example.benefit.remote

import com.example.benefit.remote.models.*
import com.example.benefit.util.AppPrefs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {


    @POST("api/user/loginnumber")
    @FormUrlEncoded
    suspend fun login(@Field("phone_number") phone_number: String): RespFormat<RespLogin>

    @POST("api/user/signup")
    @FormUrlEncoded
    suspend fun signup(
        @Field("phone") phone: String,
        @Field("referal_code") referal_code: String?,
        @Field("created") created: String = "1601573398",
        @Field("ip") ip: String = "127.0.0.1",
        @Field("referal_code") referal: String = "-",
    ): RespFormat<RegPhoneResp>


    @POST("api/user/setpassword")
    @FormUrlEncoded
    suspend fun setPassword(
        @Field("phone_number") phone_number: String,
        @Field("password") password: String,
        @Field("user_token") user_token: String,
        @Field("user_id") user_id: Int
    ): RespFormat<RespUserInfo>

    @POST("api/user/loginsms")
    suspend fun loginsms(@Body body: ReqLoginSms): RespFormat<RespLoginSms>

    @POST("api/user/logincode")
    suspend fun logincode(@Body body: ReqLoginCode): RespFormat<RespLoginCode>

    @POST("api/ordercard/one")
    @FormUrlEncoded
    suspend fun termsAccept(
        @Field("is_agree") is_agree: Boolean = true,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId,
    ): RespAcceptTerms

    @POST("api/user/checkcode")
    @FormUrlEncoded
    suspend fun checkcode(
        @Field("user_token") user_token: String,
        @Field("user_id") user_id: Int,
        @Field("phone_number") phone_number: String,
        @Field("sms_code") sms_code: String
    ): RespFormat<RespUserInfo>

    @POST("api/user/sendcode")
    @FormUrlEncoded
    suspend fun sendcode(@Field("phone_number") phone_number: String): Response<Any>


//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/site/register")
//    suspend fun userLogin(@Body loginReq: LoginRequest): Response<Any>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/site/activate")
//    suspend fun smsConfirm(@Body user: UserCredentials): Response<NUser>
////
////    @Headers("Content-Type:application/json", "Accept: application/json")
////    @POST("v1/doctor/profile/city")
////    suspend fun saveUserCity(@Body body: Map<String, String>): Response<NUser>
////
////    @Headers("Content-Type:application/json", "Accept: application/json")
////    @POST("v1/doctor/profile/update")
////    suspend fun saveUser(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/site/regions")
//    suspend fun getAllRegions(): Response<List<ERegion>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/site/cities")
//    suspend fun getAllCities(): Response<MyResponse<List<NCity>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/animal/types")
//    suspend fun getAllAnimalTypes(): Response<MyResponse<List<NAnimalType>>>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/animal/services")
//    suspend fun getServicesByAnimalType(
//        @Query("type_animal_id") type_animal_id: Int,
//        @Query("expand") expand: String = "services"
//    ): Response<MyResponse<List<ServiceWithPriceGroup>>>
//
//
////    @Headers("Content-Type:application/json", "Accept: application/json")
////    @GET("v1/doctor/get/city-districts")/*?expand=areas&city_id=1*/
////    suspend fun getDistrictsForSelectedRegion(/*@Header("Authorization") token: String,*//*@Query("city_id") city_id: Int,*/
////                                        @Query("expand") expand: String = "areas"): Response<MyResponse<List<CityWithAreas>>>
////
////
////    @Headers("Content-Type:application/json", "Accept: application/json")
////    @POST("v1/doctor/profile/update")
////    suspend fun updateProfileInfo( @Body body: UserInfo): Response<UserInfo>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("driver_post/action/filter")
//    suspend fun filterDriverPost(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String,
//        @Body filter: Filter
//    ): DriverPostsResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("passenger_post/action")
//    suspend fun createPost(
//        @Header("Authorization") token: String,
//        @Body passengerPostBody: PassengerPost
//    ): PlainResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @PUT("passenger_post/action/cancel/{identifier}")
//    suspend fun deletePost(
//        @Header("Authorization") token: String,
//        @Path(
//            value = "identifier",
//            encoded = true
//        ) identifier: String
//    ): PlainResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @PUT("passenger_post/action/finish/{identifier}")
//    suspend fun finishPost(
//        @Header("Authorization") token: String,
//        @Path(
//            value = "identifier",
//            encoded = true
//        ) identifier: String
//    ): PlainResponse
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("passenger_post/action/active")
//    suspend fun getActivePosts(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String
//    ): PassengerActivePostsResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("passenger_post/action/history")
//    suspend fun getHistoryPosts(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String,
//        @Query("page") page: Int = 0,
//        @Query("size") size: Int = 10
//    ): PassengerHistoryPostsResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("region/action")
//    suspend fun getPlacesFeed(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String,
//        @Query("query") query: String
//    ): PlaceListResponse
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("car_model/action")
//    suspend fun getCarModels(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String
//    ): CarModelsResponse
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("car_color/action")
//    suspend fun getCarColors(
//        @Header("Authorization") token: String,
//        @Header("Accept-Language") lang: String
//    ): CarColorsResponse
//
//    @Headers("Accept: application/json")
//    @Multipart
//    @POST("attach/image")
//    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse


}

