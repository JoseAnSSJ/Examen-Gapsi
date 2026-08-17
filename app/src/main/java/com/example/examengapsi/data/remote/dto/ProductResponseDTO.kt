package com.example.examengapsi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductResponseDTO(
    @SerializedName("item") val item: ItemDto
)

data class ItemDto(
    @SerializedName("props") val props: PropsDto
)

data class PropsDto(
    @SerializedName("pageProps") val pageProps: PagePropsDto
)

data class PagePropsDto(
    @SerializedName("initialData") val initialData: InitialDataDto
)

data class InitialDataDto(
    @SerializedName("searchResult") val searchResult: SearchResultDto
)

data class SearchResultDto(
    @SerializedName("itemStacks") val itemStacks: List<ItemStackDto>?,
    @SerializedName("paginationV2") val paginationV2: PaginationDto?
)

data class ItemStackDto(
    @SerializedName("items") val items: List<ProductDto>?
)

data class PaginationDto(
    @SerializedName("maxPage") val maxPage: Int
)

data class ProductDto(
    @SerializedName("usItemId") val usItemId: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("priceInfo") val priceInfo: PriceInfoDto?
)

data class PriceInfoDto(
    @SerializedName("priceDetails") val priceDetails: PriceDetailsDto?
)

data class PriceDetailsDto(
    @SerializedName("priceLines") val priceLines: List<PriceLineDto>?
)

data class PriceLineDto(
    @SerializedName("lineType") val lineType: String?,
    @SerializedName("values") val values: List<PriceValueDto>?
)

data class PriceValueDto(
    @SerializedName("key") val key: String?, @SerializedName("value") val value: String?
)