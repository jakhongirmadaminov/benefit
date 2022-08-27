package uz.magnumactive.benefit.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import splitties.experimental.ExperimentalSplittiesApi
import uz.magnumactive.benefit.remote.models.*
import uz.magnumactive.benefit.stories.data.Story
import uz.magnumactive.benefit.util.AppPrefs

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
@ExperimentalSplittiesApi
interface AuthApiService {

    @POST("api/card/report")
    @FormUrlEncoded
    suspend fun transactionsInOut(
        @Field("card_id") card_id: Long,
        @Field("startDate") startDate: Int,
        @Field("endDate") endDate: Int,
        @Field("pageSize") pageSize: Int = 100,
    ): RespFormat<TransactionInOutDTO>

    @POST("api/card/analytics")
    @FormUrlEncoded
    suspend fun transactionsAnalytics(
        @Field("card_id") card_id: Long,
        @Field("startDate") startDate: Int,
        @Field("endDate") endDate: Int,
        @Field("pageSize") pageSize: Int = 100,
    ): RespFormat<ArrayList<TransactionAnalyticsContainerDTO>>


    @GET("api/paynet/categories")
    suspend fun paymentCategories(): RespFormat<List<PaynetCategory>>

    @POST("api/paynet/paycheck")
    @FormUrlEncoded
    suspend fun paynetPayCheck(
        @Field("service_id") service_id: Long,
        @Field("provider_id") provider_id: Long,
        @Field("fields") fields: String,
    ): RespFormat<Any>

    @POST("api/paynet/paycard")
    @FormUrlEncoded
    suspend fun paynetPayCard(
        @Field("service_id") service_id: Long,
        @Field("provider_id") provider_id: Long,
        @Field("fields") fields: String,
        @Field("summa") summa: Int,
        @Field("card_id") card_id: Long,
        @Field("user_token") user_token: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<PaynetPaymentResponse>


    @POST("api/paynet/pay")
    @FormUrlEncoded
    suspend fun payWithCashback(
        @Field("service_id") service_id: Long,
        @Field("provider_id") provider_id: Long,
        @Field("fields") fields: String,
        @Field("summa") summa: Int,
        @Field("user_token") user_token: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<PaynetPaymentResponse>

    @GET("api/paynet/providers")
    suspend fun getPaynetProviders(
        @Query("id", encoded = true) id: Long
    ): RespFormat<List<PaynetMerchant>>

    @GET("api/paynet/services")
    suspend fun getPaynetServices(
        @Query("id", encoded = true) id: Long
    ): RespFormat<List<PaynetService>>

    @GET("api/background")
    suspend fun getCardBackgrounds(): RespFormat<List<CardBgDTO>>

    @GET("api/partners/category/{id}")
    suspend fun getPartnersByCategory(
        @Path(value = "id", encoded = true) id: Long
    ): RespFormat<RespPartnerInCategory>

    @GET("api/news")
    suspend fun getNews(
        @Query("page", encoded = true) page: Int,
        @Query("per-page", encoded = true) per_page: Int
    ): RespFormat<List<NewsDTO>>

    @POST("api/user/refresh")
    suspend fun updateUserInfo(@Body body: ReqUserInfo): RespFormat<RespUserInfo>

    @POST("api/user/profile")
    @FormUrlEncoded
    suspend fun getUserInfo(@Field("id") id: Long): RespFormat<ReqUserInfo>

    @POST("api/card/info")
    @FormUrlEncoded
    suspend fun getMyCardInfo(@Field("card_id") card_id: Long): RespFormat<CardsDTO>

    @POST("api/card/idtopan")
    @FormUrlEncoded
    suspend fun p2pIdToPan(
        @Field("amount") amount: Int,
        @Field("card_id") card_id: Long,
        @Field("pan") pan: String
    ): RespFormat<RespPid2Pid>

    @POST("api/card/pidtoid")
    @FormUrlEncoded
    suspend fun p2pIdToId(
        @Field("amount") amount: Int,
        @Field("card_id") card_id: String,
        @Field("recipient_id") recipient_id: String
    ): RespFormat<RespPid2Pid>

    @POST("api/card/pantoid")
    @FormUrlEncoded
    suspend fun p2pPanToId(
        @Field("amount") amount: Int,
        @Field("card_id") card_id: Long,
        @Field("pan") pan: String,
        @Field("expiry") expiry: String
    ): RespFormat<RespPid2Pid>

    @POST("api/card/balanceplus")
    @FormUrlEncoded
    suspend fun topUpBalance(
        @Field("amount") amount: Int,
        @Field("card_id") card_id: Long,
        @Field("pan") pan: String,
        @Field("expiry") expiry: String
    ): RespFormat<PlainResp>

    @POST("api/card/balancecheck")
    @FormUrlEncoded
    suspend fun topUpBalanceCode(
        @Field("topup_id") topup_id: Int,
        @Field("code") code: String
    ): RespFormat<PlainResp>

    @POST("api/card/transhistory")
    @FormUrlEncoded
    suspend fun transHistory(
        @Field("card_id") card_id: Long,
        @Field("startDate") startDate: Long,
        @Field("endDate") endDate: Long,
        @Field("pageNumber") pageNumber: Int = 0,
        @Field("pageSize") pageSize: Int = 100
    ): RespFormat<PlainResp>

    @POST("api/card/analytics")
    @FormUrlEncoded
    suspend fun transHistoryAnalytics(
        @Field("card_id") card_id: Long,
        @Field("startDate") startDate: Long,
        @Field("endDate") endDate: Long,
        @Field("pageNumber") pageNumber: Int = 0,
        @Field("pageSize") pageSize: Int = 100
    ): RespFormat<PlainResp>

    @POST("api/card/report")
    @FormUrlEncoded
    suspend fun cardReport(
        @Field("card_id") card_id: Long,
        @Field("startDate") startDate: Long,
        @Field("endDate") endDate: Long,
        @Field("pageNumber") pageNumber: Int = 0,
        @Field("pageSize") pageSize: Int = 100
    ): RespFormat<PlainResp>


    @POST("api/card/pinfo")
    @FormUrlEncoded
    suspend fun getP2PCardInfo(@Field("hpan") hpan: String): RespFormat<CardP2PDTO>

    @POST("api/refer/get")
    @FormUrlEncoded
    suspend fun getMyReferralLink(
        @Field("user_token") user_token: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespMyReferralLink

    @POST("api/ordercard/one")
    @FormUrlEncoded
    suspend fun termsAccept(
        @Field("type_id") type_id: Int,
        @Field("is_agree") is_agree: Boolean = true,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>


    @POST("api/card/new")
    suspend fun addNewCard(@Body body: ReqCard): RespFormat<RespAddCard>


    @POST("api/card/confirm")
    suspend fun confirmNewCard(@Body body: CardConfirmBody): RespFormat<Any>

    @POST("api/ordercard/two")
    @Multipart
    suspend fun addPassportPhoto(
        @Part("order_card_id") order_card_id: Long,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Long = AppPrefs.userId,
        @Part("user_auth") user_auth: String = AppPrefs.userToken!!
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/three")
    @Multipart
    suspend fun addPhotoWithPassport(
        @Part("order_card_id") order_card_id: Long,
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Long = AppPrefs.userId,
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
        @Field("order_card_id") order_card_id: Long,
        @Field("adress_text") adress_text: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/seven")
    @FormUrlEncoded
    suspend fun orderCardLimit(
        @Field("order_card_id") order_card_id: Long,
        @Field("summa") summa: String,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>

    @POST("api/ordercard/close")
    @FormUrlEncoded
    suspend fun completeOrderCard(
        @Field("order_card_id") order_card_id: Long,
        @Field("status") status: Int = 1,
        @Field("user_auth") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<RespAcceptTerms>


    //    @Cacheable
    @POST("api/card/my")
    @FormUrlEncoded
    suspend fun getMyCards(
        @Field("user_token") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<MyCardsResp>


    @POST("api/card/title")
    @FormUrlEncoded
    suspend fun changeCardTitle(
        @Field("title") title: String,
        @Field("card_id") card_id: Long
    ): RespChangeCardTitle

    @POST("api/card/design")
    @FormUrlEncoded
    suspend fun changeCardDesign(
        @Field("background_id") background_id: Int,
        @Field("card_id") card_id: Long,
        @Field("user_token") user_auth: String = AppPrefs.userToken!!,
        @Field("user_id") user_id: Long = AppPrefs.userId
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

    @POST("api/user/sendcode")
    @FormUrlEncoded
    suspend fun changePasswordGetSmsCode(
        @Field("phone_number") phone_number: String
    ): RespFormat<PlainResp>

    @POST("api/user/devicecode")
    @FormUrlEncoded
    suspend fun changePasswordSendNewCode(
        @Field("phone_number") phone_number: String,
        @Field("device_code") device_code: String
    ): RespFormat<PlainResp>

    @POST("api/user/changepassword")
    @FormUrlEncoded
    suspend fun changePassword(
        @Field("phone_number") phone_number: String,
        @Field("password") password: String,
        @Field("user_id") user_id: Long
    ): RespFormat<PlainResp>

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
    suspend fun activateCard(@Field("card_id") card_id: Long): RespActivateCard

    @POST("api/card/status")
    @FormUrlEncoded
    suspend fun blockCard(@Field("card_id") card_id: Long): RespFormat<RespActivateCard>

    @POST("api/card/remove")
    @FormUrlEncoded
    suspend fun deleteCard(@Field("card_id") card_id: Long): RespActivateCard

    @GET("api/partners/allstory/0000000000/{currentMillis}")
    suspend fun getStories(
        @Path(
            value = "currentMillis",
            encoded = true
        ) currentMillis: Long = System.currentTimeMillis()
    ): RespFormat<List<Story>>

    @POST("api/loan/loanid")
    @FormUrlEncoded
    suspend fun getLoanIdByPan(@Field("pan") pan: String): RespFormat<RespLoanId>

    @POST("api/loan/getloan")
    @FormUrlEncoded
    suspend fun getLoanInfo(@Field("id") id: Long): RespFormat<RespLoanInfo>

    @GET("api/other/currency2")
    suspend fun getCurrencies(): RespFormat<List<CurrencyDTO>>

    @GET("api/gap/index")
    suspend fun getGapGames(): RespFormat<List<GapGameDTO>>

    @POST("api/other/friends")
    @FormUrlEncoded
    suspend fun getBenefitFriends(
        @Field("phone_array") phone_array: String,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<List<BenefitContactDTO>>

    @POST("api/gap/start")
    @FormUrlEncoded
    suspend fun createGapGame(
        @Field("title") title: String,
        @Field("summa") summa: String,
        @Field("is_random") is_random: String,
        @Field("is_notif") is_notif: String,
        @Field("is_period") is_period: String,
        @Field("members") members: String,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<GapGameDTO>

    @POST("api/user/cashback")
    @FormUrlEncoded
    suspend fun getBftInfo(@Field("user_id") user_id: Long = AppPrefs.userId): RespFormat<BftInfoDTO>

    @POST("api/autopayments/my")
    @FormUrlEncoded
    suspend fun getMyAutoPayments(@Field("user_id") user_id: Long = AppPrefs.userId): RespFormat<List<AutoPaymentDTO>>


    @POST("api/autopayments/save")
    @FormUrlEncoded
    suspend fun saveAutoPayment(
        @Field("title") title: String,
        @Field("card_id") card_id: Long?,
        @Field("provider_id") provider_id: Long,
        @Field("service_id") service_id: Long,
        @Field("fields") fields: String,
        @Field("amount") amount: Int,
        @Field("type") type: Int,
        @Field("is_notify") is_notify: Boolean,
        @Field("from_cashback") from_cashback: Boolean,
        @Field("near_date") near_date: Long,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<PlainResp>

    @POST("api/partners/useraction")
    @FormUrlEncoded
    suspend fun likePartner(
        @Field("partner_id") partner_id: Long,
        @Field("type") type: String = "like",
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<PlainResp>

    @POST("api/autopayments/trash")
    @FormUrlEncoded
    suspend fun deleteRegularPayment(
        @Field("id") id: Int,
        @Field("user_id") user_id: Long = AppPrefs.userId
    ): RespFormat<PlainResp>


    @POST("api/partners/useraction")
    @FormUrlEncoded
    suspend fun likeOrDislikePartner(
        @Field("type") type: String,
        @Field("partner_id") partner_id: Long,
        @Field("user_id") user_id: Long = AppPrefs.userId,
        @Field("user_token") user_token: String = AppPrefs.userToken ?: "",
    ): PlainResp

    @POST("api/message/token")
    @FormUrlEncoded
    suspend fun sendFirebaseFCMToken(
        @Field("token") token: String,
        @Field("user_id") user_id: Long = AppPrefs.userId,
        @Field("device_type") device_type: String = "Android",
    ): PlainResp

    @POST("api/other/myfriends")
    @FormUrlEncoded
    suspend fun myFriends(
        @Field("user_id") user_id: Long = AppPrefs.userId,
    ): RespFormat<List<MyFriendDTO>>

    @POST("api/transactions/send")
    @FormUrlEncoded
    suspend fun addDividesTransaction(
        @Field("utrnno_id") utrnno_id: Long,
        @Field("utrnno_amount_id") utrnno_amount_id: Long,
        @Field("divide") divide: String,
        @Field("owner_user_id") owner_user_id: Long = AppPrefs.userId,
    ): RespFormat<PlainResp>

    @GET("api/partners/story/{partnerId}/{fromMillis}/{toMillis}")
    suspend fun getPartnerStories(
        @Path(value = "partnerId", encoded = true) partnerId: Long,
        @Path(value = "fromMillis", encoded = true) fromMillis: Long,
        @Path(value = "toMillis", encoded = true) currentMillis: Long = System.currentTimeMillis(),
    ): PlainResp


    @GET("api/partner/view/{partnerId}")
    suspend fun getPartnerInfo(
        @Path(value = "partnerId", encoded = true) partnerId: Long,
    ): RespFormat<Partner>

    @GET("api/transactions/sender-list/{user_id}")
    suspend fun mySentDivides(
        @Path(value = "user_id", encoded = true) user_id: Long,
    ): RespFormat<Partner>


    // BENEFIT marketplace
    @GET("api/market/all-category")
    suspend fun getMarketplaceCategories(): RespFormat<List<MarketPlaceCategoryObj>>

    @GET("api/market/category")
    suspend fun getMarketProductsByCategory(
        @Query(value = "category_id", encoded = true) category_id: Long,
        @Query(value = "category_sub_id", encoded = true) category_sub_id: Long? = null,
        @Query(value = "type", encoded = true) type: Int = 0,
    ): RespFormat<List<MarketProductDTO>>

    @GET("api/market/all-subcategory")
    suspend fun getSubCategory(
        @Query(value = "category_id", encoded = true) category_id: Long,
        @Query(value = "type", encoded = true) type: Int = 0,
    ): RespFormat<MarketAllSubCategoryDTO>

    @GET("api/market/main-sales")
    suspend fun getMarketMainSales(): RespFormat<List<MarketProductDTO>>

    @GET("api/market/view")
    suspend fun getProductDetails(
        @Query(value = "id", encoded = true) id: Long,
        @Query(value = "user_id", encoded = true) userId: Long = AppPrefs.userId,
    ): RespFormat<MarketProductDetailsDTO>

    @POST("api/market/my-basket")
    @FormUrlEncoded
    suspend fun getBasketList(
        @Field("user_id") user_id: Long = AppPrefs.userId,
    ): RespFormat<Partner>

    @POST("api/market/clean-basket/{user_id}")
    @FormUrlEncoded
    suspend fun cleanBasket(
        @Path(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
        @Field(value = "user_id", encoded = true) userId: Long = AppPrefs.userId,
        @Field(value = "item_id", encoded = true) item_id: Long,
    ): RespFormat<Partner>

    @POST("api/market/mincount-basket")
    @FormUrlEncoded
    suspend fun removeItemFromBasket(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
        @Field(value = "item_id", encoded = true) item_id: Long,
        @Field("count") count: Int = 1,
    ): RespFormat<Partner>

    @POST("api/market/pluscount-basket")
    @FormUrlEncoded
    suspend fun addToBasket(
        @Field("user_id") user_id: Long = AppPrefs.userId,
        @Field("item_id") item_id: Long,
        @Field("count") count: Int = 1,
    ): RespFormat<Partner>

    @POST("api/market/mincount-basket")
    @FormUrlEncoded
    suspend fun marketBeforeOrder(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
    ): RespFormat<Partner>

    @POST("api/market/create-order")
    @FormUrlEncoded
    suspend fun marketCreateOrder(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
        @Field(value = "address", encoded = true) address: String,
        @Field(value = "lat", encoded = true) lat: Double,
        @Field(value = "lan", encoded = true) lan: Double,
        @Field(value = "card_id", encoded = true) card_id: Long,
    ): RespFormat<Partner>

    @POST("api/market/pay-order")
    @FormUrlEncoded
    suspend fun marketPayOrder(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
        @Field(value = "order_id", encoded = true) order_id: Long,
    ): RespFormat<Partner>

    @POST("api/market/active-order")
    @FormUrlEncoded
    suspend fun getActiveOrders(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
    ): RespFormat<ActiveOrderResultDTO>

    @POST("api/market/history-order")
    @FormUrlEncoded
    suspend fun myMarketHistoryOrders(
        @Field(value = "user_id", encoded = true) user_id: Long = AppPrefs.userId,
    ): RespFormat<Partner>

    @POST("api/market/detail-order")
    @FormUrlEncoded
    suspend fun myMarketOrderDetails(
        @Field(value = "order_id", encoded = true) order_id: Long,
    ): RespFormat<Partner>

    @POST("api/market/index")
    @FormUrlEncoded
    suspend fun allMarketProducts(
        @Field(value = "order_id", encoded = true) order_id: Long,
    ): RespFormat<List<MarketProductDTO>>

}

