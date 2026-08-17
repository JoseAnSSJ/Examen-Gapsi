package com.example.examengapsi.domain.usecase

import com.example.examengapsi.domain.model.ProductSearchResult
import com.example.examengapsi.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        keyword: String, page: Int
    ): Result<ProductSearchResult> {
        return repository.searchProducts(keyword, page)
    }
}