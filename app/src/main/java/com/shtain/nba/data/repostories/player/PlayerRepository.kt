package com.shtain.nba.data.repostories.player

import com.shtain.nba.models.Player
import com.shtain.nba.data.network.NetworkStatus
import kotlinx.coroutines.flow.Flow


interface PlayerRepository {
    fun getPlayer(id: Long): Flow<NetworkStatus<Player>>
}