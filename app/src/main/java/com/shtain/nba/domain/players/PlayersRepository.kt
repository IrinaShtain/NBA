package com.shtain.nba.domain.players

import androidx.paging.PagingData
import com.shtain.nba.data.models.Player
import kotlinx.coroutines.flow.Flow


interface PlayersRepository {
    fun getPlayers(): Flow<PagingData<Player>>
}