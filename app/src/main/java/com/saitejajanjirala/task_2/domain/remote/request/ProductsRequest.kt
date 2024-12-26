package com.saitejajanjirala.task_2.domain.remote.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsRequest(
    @Json(name = "catgCd")
    val catgCd: List<String>? = null,
    @Json(name = "pageNo")
    val pageNo: Int? = null,
    @Json(name = "pageSize")
    val pageSize: Int? = null
)