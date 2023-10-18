package com.shtain.nba.domain.team

import com.shtain.nba.data.models.Team
import com.shtain.nba.data.network.NetworkStatus
import kotlinx.coroutines.flow.Flow


interface TeamRepository {
    fun getTeam(id: Long): Flow<NetworkStatus<Team>>
}