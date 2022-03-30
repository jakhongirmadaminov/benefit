package uz.magnumactive.benefit.remote

import uz.magnumactive.benefit.remote.repository.CardsRemote
import uz.magnumactive.benefit.util.getFormattedResponse
import uz.magnumactive.benefit.util.getParsedResponse
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */

@ExperimentalSplittiesApi
class CardsRemoteImpl @Inject constructor(
    private val api: ApiService,
    private val authApi: AuthApiService
) : CardsRemote {

    override suspend fun getCardBackgrounds() =
        getFormattedResponse { authApi.getCardBackgrounds() }

    override suspend fun deleteCard(cardId: Long) =
        getParsedResponse { authApi.deleteCard(cardId) }

    override suspend fun blockCard(cardId: Long) =
        getFormattedResponse { authApi.blockCard(cardId) }

    override suspend fun activateCard(cardId: Long) =
        getParsedResponse { authApi.activateCard(cardId) }

    override suspend fun cardTransactionHistory(
        ownId: Int,
        endDate: Long,
        startDate: Long,
        pageNumber: Int,
        pageSize: Int
    ) = getParsedResponse {
        authApi.cardTransactionHistory(ownId, endDate, startDate, pageNumber, pageSize)
    }

    override suspend fun changeCardTitle(title: String, card_id: Long) =
        getParsedResponse { authApi.changeCardTitle(title, card_id) }

    override suspend fun changeCardDesign(bgId: Int, card_id: Long) =
        getFormattedResponse { authApi.changeCardDesign(bgId, card_id) }

}