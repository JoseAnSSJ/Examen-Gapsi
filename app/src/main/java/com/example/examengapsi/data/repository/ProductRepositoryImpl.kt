package com.example.examengapsi.data.repository

import com.example.examengapsi.data.mapper.toDomain
import com.example.examengapsi.data.remote.ProductApiService
import com.example.examengapsi.domain.model.ProductSearchResult
import com.example.examengapsi.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : ProductRepository {

    override suspend fun searchProducts(
        keyword: String, page: Int
    ): Result<ProductSearchResult> {
        return try {
            val response = api.getProduct(
                keyword = keyword, page = page
            )

            val searchResult = response.item.props.pageProps.initialData.searchResult

            Result.success(searchResult.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}