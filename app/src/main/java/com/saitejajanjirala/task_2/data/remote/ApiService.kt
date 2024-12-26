package com.saitejajanjirala.task_2.data.remote

import com.saitejajanjirala.task_2.domain.remote.request.ProductsRequest
import com.saitejajanjirala.task_2.domain.remote.response.ProductResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    companion object{
        const val BASE_URL = "https://cf.msilcrm.co.in/crm-common/api/common/msil/partmaster/"
    }
    @POST("search-product-list")
    suspend fun geProductList(@Header("x-api-key") apiKey : String="BRDgjGmQf3aSM95OXXNyD5YSeZAsW1ac9N89zo1J", @Header("User-Agent") userAgent : String = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36",
       @Header("Content-Type") content : String = "application/json",
        @Body request : ProductsRequest):Response<ProductResult>
}


