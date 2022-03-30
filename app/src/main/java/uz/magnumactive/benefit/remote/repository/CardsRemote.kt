package uz.magnumactive.benefit.remote.repository

import uz.magnumactive.benefit.remote.models.CardBgDTO
import uz.magnumactive.benefit.remote.models.RespActivateCard
import uz.magnumactive.benefit.remote.models.RespChangeCardTitle
import uz.magnumactive.benefit.util.ResultWrapper

interface CardsRemote {

    suspend fun getCardBackgrounds(): ResultWrapper<List<CardBgDTO>>
    suspend fun deleteCard(cardId: Long): ResultWrapper<RespActivateCard>
    suspend fun blockCard(cardId: Long): ResultWrapper<RespActivateCard>
    suspend fun activateCard(cardId: Long): ResultWrapper<RespActivateCard>
    suspend fun cardTransactionHistory(
        ownId: Int,
        endDate: Long,
        startDate: Long,
        pageNumber: Int,
        pageSize: Int
    ): ResultWrapper<RespChangeCardTitle>

    suspend fun changeCardTitle(title: String, card_id: Long): ResultWrapper<RespChangeCardTitle>
    suspend fun changeCardDesign(bgId: Int, card_id: Long): ResultWrapper<RespChangeCardTitle>


}