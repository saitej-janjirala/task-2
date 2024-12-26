package com.saitejajanjirala.task_2.domain.repo

import com.saitejajanjirala.task_2.domain.models.Result
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun getProducts(): Flow<Result<List<ProductList>>>
}