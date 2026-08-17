package com.example.examengapsi.domain.repository

import com.example.examengapsi.domain.model.ProductSearchResult

interface ProductRepository {
    suspend fun searchProducts(keyword: String, page: Int): Result<ProductSearchResult>
}