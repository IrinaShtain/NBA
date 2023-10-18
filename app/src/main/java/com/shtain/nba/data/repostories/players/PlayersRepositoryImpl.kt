package com.shtain.nba.data.repostories.players

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shtain.nba.data.models.Player
import com.shtain.nba.data.network.conection.NetworkManagerApi
import com.shtain.nba.data.network.http.HttpClientApi
import com.shtain.nba.domain.players.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersRepositoryImpl @Inject constructor(
    private val httpClientApi: HttpClientApi,
    private val networkManagerApi: NetworkManagerApi
) : PlayersRepository {
    override fun getPlayers(): Flow<PagingData<Player>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { PlayersPagingDataSource(httpClientApi, networkManagerApi) }
        ).flow
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 35
    }
}