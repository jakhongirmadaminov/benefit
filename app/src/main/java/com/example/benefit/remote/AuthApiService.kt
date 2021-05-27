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
interface AuthApiService {


    @GET("api/paynet/categories")
    suspend fun paymentCategories(): RespFormat<List<PaynetCategory>>

    @GET("api/background")
    suspend fun getCardBackgrounds(): RespFormat<List<CardBgDTO>>

    @GET("api/partners/category/{id}")
    suspend fun getPartnersByCategory(
        @Path(value = "id", encoded = true) id: Int
    ): RespFormat<RespPartnerInCategory>

    @GET("api/news")
    suspend fun getNews(
        @Query("page", encoded = true) page: Int,
        @Query("per-page", encoded = true) per_page: Int
    ): RespFormat<List<NewsDTO>>

    @POST("api/user/refresh")
    suspend fun updateUserInfo(@Body body: ReqUserInfo): RespFormat<RespUserInfo>

    @POST("api/refer/get")
    @FormUrlEncoded
    suspend fun getMyReferralLink(
        @Field("user_token") user_token: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespMyReferralLink

    @POST("api/ordercard/one")
    @FormUrlEncoded
    suspend fun termsAccept(
        @Field("type_id") type_id: Int,
        @Field("is_agree") is_agree: Boolean = true,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>


    @POST("api/card/new")
    suspend fun addNewCard(@Body body: ReqCard): RespFormat<Any>

    @POST("api/ordercard/two")
    @Multipart
    suspend fun addPassportPhoto(
        @Part("order_card_id") order_card_id: Int,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Int = AppPrefs.userId,
        @Part("user_auth") user_auth: String = AppPrefs.userToken!!
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/three")
    @Multipart
    suspend fun addPhotoWithPassport(
        @Part("order_card_id") order_card_id: Int,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Int = AppPrefs.userId,
        @Part("user_auth") user_auth: String = AppPrefs.userToken!!
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/four")
    @Multipart
    suspend fun addWorkProof(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): RespFormat<RespAcceptTerms>

    @POST("api/user/avatar")
    @Multipart
    suspend fun uploadAvatar(@PartMap body: MutableMap<String, @JvmSuppressWildcards RequestBody>): RespFormat<RespUserInfo>

    @POST("api/ordercard/five")
    @FormUrlEncoded
    suspend fun orderCardAddress(
        @Field("order_card_id") order_card_id: Int,
        @Field("adress_text") adress_text: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/seven")
    @FormUrlEncoded
    suspend fun orderCardLimit(
        @Field("order_card_id") order_card_id: Int,
        @Field("summa") summa: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/close")
    @FormUrlEncoded
    suspend fun completeOrderCard(
        @Field("order_card_id") order_card_id: Int,
        @Field("status") status: Int = 1,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>


    @POST("api/card/my")
    @FormUrlEncoded
    suspend fun getMyCards(
        @Field("user_token") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<MyCardsResp>


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
        @Field("user_token") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Int = AppPrefs.userId
    ): RespFormat<RespChangeCardTitle>

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
    suspend fun getAllBankBranches(): RespFormat<List<BankBranchDTO>>

    @GET("api/partners")
    suspend fun getPartners(): Response<List<Partner>>


    @GET("/api/category")
    suspend fun getPartnersCategory(): RespFormat<List<PartnerCategoryDTO>>


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
    suspend fun blockCard(@Field("card_id") card_id: Int): RespFormat<RespActivateCard>

    @POST("api/card/remove")
    @FormUrlEncoded
    suspend fun deleteCard(@Field("card_id") card_id: Int): RespActivateCard


}

