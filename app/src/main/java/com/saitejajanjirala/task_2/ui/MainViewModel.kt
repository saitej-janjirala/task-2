package com.saitejajanjirala.task_2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import com.saitejajanjirala.task_2.domain.repo.ProductsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.saitejajanjirala.task_2.domain.models.Result
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(private val productsRepo: ProductsRepo):ViewModel()
{
    private val _state = MutableStateFlow<Result<List<ProductList>>>(Result.Loading(false))
    val state : StateFlow<Result<List<ProductList>>>
        get() = _state
    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            productsRepo.getProducts().collect{
                _state.value = it
            }
        }
    }
}