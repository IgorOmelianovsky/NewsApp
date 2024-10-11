package com.example.p57_news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.p57_news.model.Article
import com.example.p57_news.util.AppConstants

@TypeConverters(AppTypeConverters::class)
@Database(entities = [Article::class], version = AppConstants.DB_VERSION)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        fun getDatabase(context: Context): MainDatabase {
            val database = Room.databaseBuilder(
                context,
                MainDatabase::class.java,
                AppConstants.DB_NAME
            ).build()
            return database
        }
    }

}