package com.saitejajanjirala.task_2.domain.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResult(
    @Json(name = "data")
    val `data`: Data? = null,
    @Json(name = "error")
    val error: Boolean? = null,
    @Json(name = "errors")
    val errors: Any? = null
)