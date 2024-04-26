package com.rudachenkoroman.astonIntensivFinal.api

import com.rudachenkoroman.astonIntensivFinal.model.news.NewsResponse
import com.rudachenkoroman.astonIntensivFinal.model.source.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String,
        @Query("category")
        category: String,
        @Query("apiKey")
        api: String = API_KEY_TWO,
    ): Response<NewsResponse>

    @GET("/v2/top-headlines/sources")
    suspend fun getSource(
        @Query("country")
        countryCode: String,
        @Query("apiKey")
        api: String = API_KEY_TWO,
    ): Response<SourceResponse>

    @GET("/v2/everything")
    suspend fun getDetailSourceNews(
        @Query("q")
        searchQuery: String,
        @Query("searchIn")
        searchIn: String ,
        @Query("sources")
        sources: String,
        @Query("apiKey")
        apiKey: String = API_KEY_TWO,
    ): Response<NewsResponse>

    @GET("/v2/top-headlines/")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String = "us",
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String = result,
        @Query("apiKey")
        apiKey: String = API_KEY_TWO
    ): Response<NewsResponse>

    companion object{
        private const val API_KEY_ONE = "6278be60e04a4607811df26a69b236a8"
        private const val API_KEY_TWO = "0d33c096900c4c4097ce0ba7700cb02e"
        private const val API_KEY_THREE ="0300182d74954aafb0a63224ff3f5723"
        private const val QUERY_PAGE_SIZE = "20"
        var result = " "
    }
}

