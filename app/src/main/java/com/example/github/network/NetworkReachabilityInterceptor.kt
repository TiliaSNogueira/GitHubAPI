package com.example.github.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkNotAvailableException : IOException("Internet connection not available")

interface NetworkStateChecker {
    fun isNetworkAvailable(): Boolean
}

class NetworkStateCheckerImpl(private val context: Context) : NetworkStateChecker {
    override fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

class NetworkReachabilityInterceptor(private val networkStateChecker: NetworkStateChecker) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkStateChecker.isNetworkAvailable()) {
            throw NetworkNotAvailableException()
        }
        val request = chain.request()
        return chain.proceed(request)
    }
}
