package com.example.benefit.remote.repository

import com.example.benefit.remote.models.CardBgDTO
import com.example.benefit.remote.models.CardDesignDTO
import com.example.benefit.remote.models.RespActivateCard
import com.example.benefit.remote.models.RespChangeCardTitle
import com.example.benefit.util.ResultWrapper

interface CardsRemote {

    suspend fun getCardBackgrounds(): ResultWrapper<List<CardBgDTO>>
    suspend fun deleteCard(cardId: Int): ResultWrapper<RespActivateCard>
    suspend fun blockCard(cardId: Int): ResultWrapper<RespActivateCard>
    suspend fun activateCard(cardId: Int): ResultWrapper<RespActivateCard>
    suspend fun cardTransactionHistory(
        ownId: Int,
        endDate: Long,
        startDate: Long,
        pageNumber: Int,
        pageSize: Int
    ): ResultWrapper<RespChangeCardTitle>

    suspend fun changeCardTitle(title: String, card_id: Int): ResultWrapper<RespChangeCardTitle>
    suspend fun changeCardDesign(bgId: Int, card_id: Int): ResultWrapper<RespChangeCardTitle>


}