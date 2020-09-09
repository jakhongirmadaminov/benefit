package com.example.benefit.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface AuthorizedApiService {


    @POST("api/user/edit")
    @Multipart
    suspend fun editUserInfo(@Field("phone_number") phone_number: String): Response<Any>

    @POST("api/user/setpassword")
    @FormUrlEncoded
    suspend fun setPassword(@Field("phone_number") phone_number: String): Response<Any>

    @Multipart
    @POST("api/user/loginnumber")
    suspend fun uploadAvatar(@Part("phone_number") phone_number: String): Response<Any>

//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile/city")
//    suspend fun saveUserCity(@Body body: Map<String, String>): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile/update")
//    suspend fun saveUser(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): Response<NUser>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/get/city-districts")
//    suspend fun getDistrictsForSelectedRegion(@Query("expand") expand: String = "areas"): Response<MyResponse<List<CityWithAreas>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/payout/cards")
//    suspend fun getMyCards(): Response<MyResponse<List<Card>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/call/new")
//    suspend fun getMyNewCalls(@Query("expand") expand: String = "info,photos,detail"): Response<MyResponse<List<NewCall>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/get/reviews")
//    suspend fun getMyReviews(): Response<DoctorReview>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile")
//    suspend fun getMySchedule(@Query("expand") expand: String = "schedule"): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile")
//    suspend fun getMyVisitAddresses(@Query("expand") expand: String = "visitAddresses"): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile")
//    suspend fun getMyVisitPrices(@Query("expand") expand: String = "visitPrices"): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile")
//    suspend fun getMyServicePrices(@Query("expand") expand: String = "servicePrices"): Response<NUser>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/call/agree")
//    suspend fun acceptCall(@Query("disease_id",
//                                  encoded = true) disease_id: String): Response<MyResponse<ResponseBody>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/call/reject")
//    suspend fun rejectCall(@Query("disease_id",
//                                  encoded = true) disease_id: String): Response<MyResponse<ResponseBody>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile/change-status")
//    suspend fun toggleStatus(): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/call/my")
//    suspend fun getMyCallsHistory(@Query("page") page: Int,
//                                  @Query("expand", encoded = true) expand: String = "info,photos,detail",
//                                  @Query("status",
//                                         encoded = true) status: String = "7"): Response<MyResponse<List<NewCall>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/call/my")
//    suspend fun getMyCallsActive(
//        @Query("expand", encoded = true) expand: String = "info,photos,detail",
//        @Query("status",
//               encoded = true) status: String = "3,8,6,11,12,13,14"): Response<MyResponse<List<NewCall>>>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile/update")
//    suspend fun updateProfileInfo(@Body body: UserInfo): Response<UserInfo>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile")
//    suspend fun getProfileInfo(@Query("expand",
//                                      encoded = true) expand: String = "documents,region"): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/price/service")
//    suspend fun addServicePrices(@Body body: List<IdPriceBody>): Response<ResponseBody>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("v1/doctor/profile/add-education")
//    suspend fun addEducation(@Body body: EducationBody): Response<NUser>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @PUT("v1/doctor/profile/visit-areas")
//    suspend fun updateMyAreas(@Body body: List<Int>): Response<NUser>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @PUT("v1/doctor/price/visit")
//    suspend fun updateVisitPrices(@Body body: ReqVisitPrices): Response<ResponseBody>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @PUT("v1/doctor/schedule/add")
//    suspend fun updateSchedule(@Body body: MyScheduleBody): Response<ResponseBody>
//
//
//    @Multipart
//    @POST("v1/doctor/profile/avatar")
//    suspend fun updateUserAvatar(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): Response<NUser>
//
//    @Multipart
//    @POST("v1/doctor/profile/add-passport")
//    suspend fun addPassport(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): Response<NUser>
//
//    @Multipart
//    @POST("v1/doctor/profile/add-document")
//    suspend fun addDocFiles(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): Response<NUser>
//
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile/delete-document")
//    suspend fun deleteDoc(@Query("document_id",
//                                 encoded = true) document_id: Int): Response<ResponseBody>
//
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @GET("v1/doctor/profile")
//    suspend fun getAllDocs(@Query("expand",
//                                  encoded = true) expand: String = "documents"): Response<NUser>


}

