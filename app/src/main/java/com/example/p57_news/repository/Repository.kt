package com.example.p57_news.repository

import com.example.p57_news.api.RetrofitInstance
import com.example.p57_news.db.MainDatabase
import com.example.p57_news.model.Article
import com.example.p57_news.model.MainResponse

class Repository(private val db: MainDatabase) {

    suspend fun insertArticle(article: Article) = db.getDao().insertArticle(article)

    suspend fun deleteArticle(article: Article) = db.getDao().deleteArticle(article)

    fun readArticles() = db.getDao().readArticles()

    suspend fun getTopHeadlines(category: String): MainResponse {
        val api = RetrofitInstance.getApi()
        val response = api.getTopHeadlines(category)
        return response
    }

    suspend fun getEverything(query: String): MainResponse {
        val api = RetrofitInstance.getApi()
        val response = api.getEverything(query)
        return response
    }

}