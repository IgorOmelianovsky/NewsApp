package com.example.p57_news.util

import com.example.p57_news.model.MainResponse

sealed class Resource {
    data object Loading : Resource()
    data class Success(val response: MainResponse) : Resource()
    data class Error(val message: String) : Resource()
}