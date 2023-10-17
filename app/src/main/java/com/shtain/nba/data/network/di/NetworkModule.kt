package com.shtain.nba.data.network.di

import com.shtain.nba.data.network.conection.NetworkManagerApi
import com.shtain.nba.data.network.conection.NetworkManagerImpl
import com.shtain.nba.data.network.http.HttpClientApi
import com.shtain.nba.data.network.http.HttpClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun provideHttpClient(httpClient: HttpClientImpl): HttpClientApi

    @Binds
    abstract fun provideNetworkManager(manager: NetworkManagerImpl): NetworkManagerApi
}


