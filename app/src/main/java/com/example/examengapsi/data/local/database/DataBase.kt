package com.example.examengapsi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examengapsi.data.local.dao.SearchHistoryDao
import com.example.examengapsi.data.local.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao
}