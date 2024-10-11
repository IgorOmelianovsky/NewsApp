package com.example.p57_news.api

import com.example.p57_news.model.MainResponse
import com.example.p57_news.util.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = AppConstants.API_KEY,
    ): MainResponse

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = AppConstants.API_KEY,
    ): MainResponse

}