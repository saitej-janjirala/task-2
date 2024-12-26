package com.saitejajanjirala.task_2.domain.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "CURRENT_PAGE_NO")
    val cURRENTPAGENO: Int? = null,
    @Json(name = "PRODUCT_LIST")
    val productList: List<ProductList>? = null,
    @Json(name = "TOTAL_PAGES")
    val totalPages: Int? = null,
    @Json(name = "TOTAL_RECORDS")
    val totalRecords: Int? = null
)