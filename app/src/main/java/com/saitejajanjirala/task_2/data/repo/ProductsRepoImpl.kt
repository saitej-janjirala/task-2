package com.saitejajanjirala.task_2.data.repo

import com.saitejajanjirala.task_2.data.remote.ApiService
import com.saitejajanjirala.task_2.di.NoInternetException
import com.saitejajanjirala.task_2.domain.models.Result
import com.saitejajanjirala.task_2.domain.remote.request.ProductsRequest
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import com.saitejajanjirala.task_2.domain.repo.ProductsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(private val apiService: ApiService):ProductsRepo {
    override suspend fun getProducts(): Flow<Result<List<ProductList>>> = flow{
        val productsRequest = ProductsRequest(
            listOf("AA"),
            1,
            10
        )
        emit(Result.Loading(true))
        val response = apiService.geProductList(request = productsRequest)
        emit(Result.Loading(false))
        if(response.isSuccessful){
            val body = response.body()
            body?.data?.productList?.let {
                if(it.size>0) {
                    emit(Result.Success(it))
                }
                else{
                    emit(Result.Error("No Data Found"))
                }
            }?:emit(Result.Error("No Data Found"))

        }
        else{
            emit(Result.Error(response.message()))
        }

    }.catch{e->
        when(e){
            is NoInternetException->{
                emit(Result.Error("Please Check your internet Connection"))
            }
            else->{
                emit(Result.Error("Unknown Error Occured"))
            }
        }
    }
}