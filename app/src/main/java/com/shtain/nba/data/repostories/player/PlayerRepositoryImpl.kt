package com.shtain.nba.data.repostories.player

import com.shtain.nba.data.models.Player
import com.shtain.nba.data.network.NetworkStatus
import com.shtain.nba.data.network.conection.NetworkConstants
import com.shtain.nba.data.network.conection.NetworkManagerApi
import com.shtain.nba.data.network.http.HttpClientApi
import com.shtain.nba.domain.player.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class PlayerRepositoryImpl @Inject constructor(
    private val httpClient: HttpClientApi,
    private val networkManager: NetworkManagerApi,
) : PlayerRepository {
    override fun getPlayer(id: Long): Flow<NetworkStatus<Player>> {
        return flow {
            if (networkManager.isConnected().not()) {
                emit(NetworkStatus.NoInternet)
            } else {
                emit(NetworkStatus.Loading)

                val response = httpClient.getNBAPlayersApi().getPlayer(id)

                emit(NetworkStatus.Success(response))
            }
        }.catch { throwable ->
            emit(NetworkStatus.Error(throwable.message ?: NetworkConstants.DEFAULT_ERROR_MESSAGE))
        }.flowOn(
            Dispatchers.IO
        )
    }
}