package com.example.benefit.remote

import com.example.benefit.remote.models.*
import com.example.benefit.util.AppPrefs
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import splitties.experimental.ExperimentalSplittiesApi

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
@ExperimentalSplittiesApi
interface AuthorizedApiService {


    @GET("api/paynet/categories")
    suspend fun paymentCategories(): RespFormatter<List<PaynetCategory>>

    @GET("api/background")
    suspend fun getCardBackgrounds(): RespFormatter<List<CardBgDTO>>

    @GET("api/partners/category/{id}")
    suspend fun getPartnersByCategory(
        @Path(value = "id", encoded = true) id: Int
    ): RespPartnerInCategory

    @GET("api/news")
    suspend fun getNews(
        @Query("page", encoded = true) page: Int,
        @Query("per-page", encoded = true) per_page: Int
    ): RespFormatter<List<NewsDTO>>

    @POST("api/user/edit")
    @Multipart
    suspend fun editUserInfo(@Field("phone_number") phone_number: String): Response<Any>

    @POST("api/user/setpassword")
    @FormUrlEncoded
    suspend fun setPassword(@Field("phone_number") phone_number: String): Response<Any>

    @POST("api/refer/get")
    @FormUrlEncoded
    suspend fun getMyReferralLink(
        @Field("user_token") user_token: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespMyReferralLink

    @POST("api/ordercard/one")
    @FormUrlEncoded
    suspend fun termsAccept(
        @Field("is_agree") is_agree: Boolean = true,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespAcceptTerms

    @POST("api/ordercard/two")
    @Multipart
    suspend fun addPassportPhoto(
        @Part("order_card_id") order_card_id: Int,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Int = AppPrefs.userId,
        @Part("user_auth") user_auth: String = AppPrefs.userToken!!
    ): RespAcceptTerms

    @POST("api/ordercard/three")
    @Multipart
    suspend fun addPhotoWithPassport(
        @Part("order_card_id") order_card_id: Int,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Int = AppPrefs.userId,
        @Part("user_auth") user_auth: String = AppPrefs.userToken!!
    ): RespAcceptTerms

    @POST("api/ordercard/four")
    @Multipart
    suspend fun addWorkProof(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): RespAcceptTerms

    @POST("api/ordercard/five")
    @FormUrlEncoded
    suspend fun orderCardAddress(
        @Field("order_card_id") order_card_id: Int,
        @Field("adress_text") adress_text: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespAcceptTerms


    @POST("api/ordercard/seven")
    @FormUrlEncoded
    suspend fun orderCardLimit(
        @Field("order_card_id") order_card_id: Int,
        @Field("summa") summa: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespAcceptTerms

    @POST("api/ordercard/close")
    @FormUrlEncoded
    suspend fun completeOrderCard(
        @Field("order_card_id") order_card_id: Int,
        @Field("status") status: Int = 1,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespAcceptTerms


    @POST("api/card/my")
    @FormUrlEncoded
    suspend fun getMyCards(
        @Field("user_token") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormatter<List<CardDTO>>


    @POST("api/card/title")
    @FormUrlEncoded
    suspend fun changeCardTitle(
        @Field("title") title: String,
        @Field("card_id") card_id: Int
    ): RespChangeCardTitle

    @POST("api/card/design")
    @FormUrlEncoded
    suspend fun changeCardDesign(
        @Field("background_id") background_id: Int,
        @Field("card_id") card_id: Int,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormatter<RespChangeCardTitle>

    @POST("api/card/transhistory")
    @FormUrlEncoded
    suspend fun cardTransactionHistory(
        @Field("card_id") ownId: Int,
        @Field("endDate") endDate: Long,
        @Field("startDate") startDate: Long,
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int
    ): RespChangeCardTitle


    @GET("api/other/branches")
    suspend fun getAllBankBranches(): List<BankBranchDTO>


    @POST("api/card/new")
    @FormUrlEncoded
    suspend fun addCard(
        @Field("title") title: String,
        @Field("expiry") expiry: Int,
        @Field("pan") pan: Long,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespAcceptTerms


    @GET("api/partners")
    suspend fun getPartners(): Response<List<Partner>>


    @GET("/api/category")
    suspend fun getPartnersCategory(): Response<List<PartnerCategoryDTO>>


    @GET("/api/category/children/{id}")
    suspend fun getCategoryChildren(
        @Path(
            value = "id",
            encoded = true
        ) id: Int
    ): Response<List<PartnerCategoryDTO>>


    @POST("api/card/statusactive")
    @FormUrlEncoded
    suspend fun activateCard(@Field("card_id") card_id: Int): RespActivateCard

    @POST("api/card/status")
    @FormUrlEncoded
    suspend fun blockCard(@Field("card_id") card_id: Int): RespFormatter<RespActivateCard>

    @POST("api/card/remove")
    @FormUrlEncoded
    suspend fun deleteCard(@Field("card_id") card_id: Int): RespActivateCard

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

