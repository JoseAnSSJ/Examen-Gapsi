package com.example.examengapsi.data.mapper

import com.example.examengapsi.data.remote.dto.ProductDto
import com.example.examengapsi.data.remote.dto.SearchResultDto
import com.example.examengapsi.domain.model.Product
import com.example.examengapsi.domain.model.ProductSearchResult

fun ProductDto.toDomain(): Product? {

    val productId = usItemId ?: id ?: return null
    val productName = name ?: return null

    val priceLines = priceInfo
        ?.priceDetails
        ?.priceLines
        .orEmpty()

    val currentPrice = priceLines
        .firstOrNull { it.lineType == "CURRENT_PRICE" }
        ?.values
        ?.firstOrNull { it.key == "PRICE" }
        ?.value
        ?.toDoubleOrNull()

    val discountedPrice = priceLines
        .firstOrNull { it.lineType == "DISCOUNTED_PRICE" }
        ?.values
        ?.firstOrNull { it.key == "PRICE" }
        ?.value
        ?.toDoubleOrNull()

    val lowPrice = priceLines
        .firstOrNull {
            it.lineType == "OPTIONS" ||
                    it.lineType == "OPTIONS_RANGE"
        }
        ?.values
        ?.firstOrNull { it.key == "LOW_PRICE" }
        ?.value
        ?.toDoubleOrNull()

    val price = currentPrice
        ?: discountedPrice
        ?: lowPrice
        ?: return null

    if (price <= 0.0) return null

    return Product(
        id = productId,
        name = productName,
        imageUrl = image.orEmpty(),
        price = price
    )
}

fun SearchResultDto.toDomain(): ProductSearchResult {
    val products = itemStacks
        .orEmpty()
        .flatMap { it.items.orEmpty() }
        .mapNotNull { it.toDomain() }

    return ProductSearchResult(
        products = products,
        totalPages = paginationV2?.maxPage ?: 1
    )
}