package com.shtain.nba.data.repostories.di

import com.shtain.nba.data.repostories.player.PlayerRepositoryImpl
import com.shtain.nba.data.repostories.players.PlayersRepositoryImpl
import com.shtain.nba.data.repostories.team.TeamRepositoryImpl
import com.shtain.nba.domain.player.PlayerRepository
import com.shtain.nba.domain.players.PlayersRepository
import com.shtain.nba.domain.team.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providePlayersRepository(repo: PlayersRepositoryImpl): PlayersRepository

    @Binds
    abstract fun providePlayerRepository(repo: PlayerRepositoryImpl): PlayerRepository

    @Binds
    abstract fun provideTeamRepository(repo: TeamRepositoryImpl): TeamRepository
}