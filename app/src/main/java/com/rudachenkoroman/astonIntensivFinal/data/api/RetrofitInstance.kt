package com.rudachenkoroman.astonIntensivFinal.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object{
        private const val BASE_URL = "https://newsapi.org/"
        private const val PING_INTERVAL = 3L
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: NewsApi by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }

}