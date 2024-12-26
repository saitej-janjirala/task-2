package com.saitejajanjirala.task_2.domain.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductList(
    @Json(name = "ADDRESS")
    val aDDRESS: String? = null,
    @Json(name = "CATG_TYPE")
    val cATGTYPE: String? = null,
    @Json(name = "DISCOUNT_PERCENTAGE")
    val dISCOUNTPERCENTAGE: Int? = null,
    @Json(name = "L1_CODE")
    val l1CODE: String? = null,
    @Json(name = "L1_DESC")
    val l1DESC: String? = null,
    @Json(name = "L2_CODE")
    val l2CODE: String? = null,
    @Json(name = "L2_DESC")
    val l2DESC: String? = null,
    @Json(name = "L3_CODE")
    val l3CODE: String? = null,
    @Json(name = "L3_DESC")
    val l3DESC: String? = null,
    @Json(name = "NON_DISCOUNTED_PRICE")
    val nONDISCOUNTEDPRICE: Int? = null,
    @Json(name = "OUT_OF_STOCK")
    val oUTOFSTOCK: String? = null,
    @Json(name = "PART_MRP")
    val pARTMRP: Double? = null,
    @Json(name = "PART_NAME")
    val pARTNAME: String? = null,
    @Json(name = "PART_NUM")
    val pARTNUM: String? = null,
    @Json(name = "QTY")
    val qTY: Int? = null,
    @Json(name = "ROOT_PART_NUM")
    val rOOTPARTNUM: String? = null,
    @Json(name = "SALES_EFF_DATE")
    val sALESEFFDATE: String? = null,
    @Json(name = "THUMBNAIL_IMAGE_URL")
    val tHUMBNAILIMAGEURL: String? = null,
    @Json(name = "UNIT")
    val uNIT: String? = null,
    @Json(name = "UNIT_RATE")
    val uNITRATE: Double? = null
)