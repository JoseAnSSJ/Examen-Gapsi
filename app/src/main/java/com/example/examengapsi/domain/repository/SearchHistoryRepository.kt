package com.example.examengapsi.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getHistory(): Flow<List<String>>

    suspend fun saveSearch(query: String)

    suspend fun deleteSearch(query: String)

    suspend fun clearHistory()
}