package com.shtain.nba.data.network.http

import com.google.gson.GsonBuilder
import com.shtain.nba.data.network.api.NBAPlayersApiService
import com.shtain.nba.data.network.conection.NetworkConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HttpClientImpl @Inject constructor() : HttpClientApi {

    private lateinit var financialApi: NBAPlayersApiService

    /**
     * @return [NBAPlayersApiService] for making network requests
     */
    override fun getNBAPlayersApi(): NBAPlayersApiService {
        /**
         * init [financialApi] if it wasn't initialized or a user changed url in the settings fragment
         */
        if (this::financialApi.isInitialized.not()) {
            financialApi =
                getApi(NBAPlayersApiService::class.java)
        }
        return financialApi
    }

    private fun <Api> getApi(
        _class: Class<Api>
    ): Api {
        return getRetrofit().create(_class)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(getOkHttpBuilder().build())
            .baseUrl(BASE_URL)
            .build()
    }

    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .also { builder ->
                //    if (BuildConfig.DEBUG) {
                builder.addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                //  }
            }
    }

}