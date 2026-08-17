package com.example.examengapsi.core.local.di

import android.content.Context
import androidx.room.Room
import com.example.examengapsi.data.local.dao.SearchHistoryDao
import com.example.examengapsi.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "gapsi_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(
        database: AppDatabase
    ): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}