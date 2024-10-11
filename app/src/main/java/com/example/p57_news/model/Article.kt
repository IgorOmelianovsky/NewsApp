package com.example.p57_news.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.p57_news.util.AppConstants

@Entity(tableName = AppConstants.TABLE_NAME)
data class Article(
    val source: Source,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
