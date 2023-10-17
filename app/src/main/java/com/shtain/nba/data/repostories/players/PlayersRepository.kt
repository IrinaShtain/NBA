package com.shtain.nba.data.repostories.players

import androidx.paging.PagingData
import com.shtain.nba.models.Player
import kotlinx.coroutines.flow.Flow


interface PlayersRepository {
    fun getPlayers(): Flow<PagingData<Player>>
}