package com.sbnri.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceHelper {

    private const val BASE_URL = "https://api.github.com/orgs/octokit/"
    private val logging = HttpLoggingInterceptor()

    fun getRetrofitClient() : APIClient {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        var okHttpClient : OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build()

        var retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(APIClient::class.java)
    }
}