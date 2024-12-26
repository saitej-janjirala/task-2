package com.saitejajanjirala.task_2.domain.models


sealed class Result<T>(val data : T?=null, val isLoading : Boolean = false, val msg : String?=null){
    data class Success<T>(val res : T): Result<T>(data = res)
    data class Loading<T>(val loading : Boolean) : Result<T>(isLoading = loading)
    data class Error<T>(val m : String?): Result<T>(msg = m)
}