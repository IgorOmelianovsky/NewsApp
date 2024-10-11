package com.example.p57_news.db

import androidx.room.TypeConverter
import com.example.p57_news.model.Source

class AppTypeConverters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name)
    }

}