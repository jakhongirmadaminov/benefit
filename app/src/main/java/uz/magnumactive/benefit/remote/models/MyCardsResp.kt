package uz.magnumactive.benefit.remote.models

import com.google.gson.annotations.SerializedName

data class MyCardsResp(
    @SerializedName("bank") val bank: List<List<CardBankDTO>>,
    @SerializedName("benefit") val benefit: List<CardDTO>,
) {


    fun getProperly(): List<CardDTO> {
        val bankCards = bank.flatten().associateBy { it.idString }

        benefit.forEachIndexed { index, cardDTO ->
            cardDTO.balance = bankCards[cardDTO.own_id!!]!!.balance
        }
        return benefit
    }

}
