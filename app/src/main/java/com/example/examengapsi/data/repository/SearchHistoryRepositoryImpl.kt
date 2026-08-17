package com.example.examengapsi.data.repository

import com.example.examengapsi.data.local.dao.SearchHistoryDao
import com.example.examengapsi.data.local.entity.SearchHistoryEntity
import com.example.examengapsi.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val dao: SearchHistoryDao
) : SearchHistoryRepository {

    override fun getHistory(): Flow<List<String>> {
        return dao.getHistory().map { history ->
            history.map { it.query }
        }
    }

    override suspend fun saveSearch(query: String) {
        dao.insert(
            SearchHistoryEntity(
                query = query.trim(), searchedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteSearch(query: String) {
        dao.delete(query)
    }

    override suspend fun clearHistory() {
        dao.clear()
    }
}