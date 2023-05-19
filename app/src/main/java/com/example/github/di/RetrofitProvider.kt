package com.example.github.di

import android.content.Context
import com.example.github.Constants.BASE_URL
import com.example.github.network.NetworkReachabilityInterceptor
import com.example.github.network.NetworkStateChecker
import com.example.github.network.NetworkStateCheckerImpl
import com.example.github.remote.api.GitHubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider(context: Context) {


    private val networkStateChecker by lazy {
        NetworkStateCheckerImpl(context = context)
    }

    private val okHttpClient by lazy {
        provideOkHttpClient(networkStateChecker)
    }

    private val retrofit by lazy {
        provideRetrofit(okHttpClient = okHttpClient)
    }

    private fun provideOkHttpClient(networkStateChecker: NetworkStateChecker): OkHttpClient {
        val timeOutInSeconds = 120
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeOutInSeconds.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOutInSeconds.toLong(), TimeUnit.SECONDS)
            .addInterceptor(NetworkReachabilityInterceptor(networkStateChecker))

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        return builder.build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: GitHubAPI by lazy {
        retrofit.create(GitHubAPI::class.java)
    }


}