package com.example.examengapsi.data.remote

import com.example.examengapsi.data.remote.dto.ProductResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("walmart-search-by-keyword")
    suspend fun getProduct(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("sortBy") sortBy: String = "best_match"
    ): ProductResponseDTO
}