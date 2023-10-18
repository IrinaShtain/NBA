package com.shtain.nba.data.repostories.players

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shtain.nba.data.models.Player
import com.shtain.nba.data.network.NetworkException
import com.shtain.nba.data.network.conection.NetworkManagerApi
import com.shtain.nba.data.network.http.HttpClientApi

class PlayersPagingDataSource(private val nbaPlayersApiService: HttpClientApi,
                              private val networkManagerApi: NetworkManagerApi
) :
    PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = nbaPlayersApiService.getNBAPlayersApi()
                .getPlayers(page, params.loadSize)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.meta.totalPages == page) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(if (networkManagerApi.isConnected()) exception  else NetworkException())
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}