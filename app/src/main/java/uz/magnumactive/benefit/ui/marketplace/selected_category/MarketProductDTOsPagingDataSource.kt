package uz.magnumactive.benefit.ui.marketplace.selected_category

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.remote.models.MarketProductDTO

class MarketProductDTOsPagingDataSource(
    val selectedCatgId: Long,
    val filterSelectionType: Int,
    private val service: AuthApiService
) :
    PagingSource<Int, MarketProductDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarketProductDTO> {
        val pageNumber = params.key ?: 1

        return try {
            val response = service.getMarketProductsByCategory(
                category_id = selectedCatgId,
                type = filterSelectionType,
                page = pageNumber
            )
            val pagedResponse = response.result
            val data = pagedResponse?.data

            LoadResult.Page(
                data = data!!,
                prevKey = if (pageNumber < 1) null else pageNumber - 1,
                nextKey = /*if (ceil(response.result.data!!.pageCount!!).toInt() > pageNumber)*/ pageNumber + 1/* else null*/
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MarketProductDTO>): Int? {
        return null
    }
}