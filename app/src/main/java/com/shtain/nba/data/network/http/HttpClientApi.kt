package com.shtain.nba.data.network.http

import com.shtain.nba.data.network.api.NBAPlayersApiService


interface HttpClientApi {
    fun getNBAPlayersApi(): NBAPlayersApiService
}