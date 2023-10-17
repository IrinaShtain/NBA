package com.shtain.nba.data.network

import java.io.IOException


sealed class NetworkStatus<out T> {

    object Loading : NetworkStatus<Nothing>()

    object NoInternet : NetworkStatus<Nothing>()

    class Error(val message: String) : NetworkStatus<Nothing>()

    class Success<T>(val data: T) : NetworkStatus<T>()

}

class NetworkException :IOException ()