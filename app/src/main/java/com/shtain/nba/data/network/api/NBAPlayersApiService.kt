package com.shtain.nba.data.network.api

import com.shtain.nba.data.models.Player
import com.shtain.nba.data.models.PlayerListResponse
import com.shtain.nba.data.models.Team
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NBAPlayersApiService {
    @GET("players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PlayerListResponse

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: Long): Player

    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: Long): Team
}