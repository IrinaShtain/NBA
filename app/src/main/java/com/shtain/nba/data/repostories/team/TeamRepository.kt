package com.shtain.nba.data.repostories.team

import com.shtain.nba.models.Team
import com.shtain.nba.data.network.NetworkStatus
import kotlinx.coroutines.flow.Flow


interface TeamRepository {
    fun getTeam(id: Long): Flow<NetworkStatus<Team>>
}