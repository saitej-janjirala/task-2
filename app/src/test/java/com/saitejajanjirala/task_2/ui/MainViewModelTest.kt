package com.saitejajanjirala.task_2.ui

import app.cash.turbine.test
import com.saitejajanjirala.task_2.domain.models.Result
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import com.saitejajanjirala.task_2.domain.repo.ProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    @Mock
    lateinit var repository : ProductsRepo
    lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `repository returns loading then success data`()= runTest{
        val sampleProduct = ProductList(
            aDDRESS = "Manufacturer's Name and Address :/Maruti Suzuki India Limited/#1,Nelson Mandela Road,Vasant Kunj, New Delhi-110070",
            cATGTYPE = "AA",
            dISCOUNTPERCENTAGE = 0,
            l1CODE = "EXT",
            l1DESC = "Exterior",
            l2CODE = "WHL",
            l2DESC = "Alloy Wheels",
            l3CODE = null,
            l3DESC = null,
            nONDISCOUNTEDPRICE = 0,
            oUTOFSTOCK = "Y",
            pARTMRP = 10390.00,
            pARTNAME = "ALLOY WHEEL",
            pARTNUM = "43210M68PM1-QC8",
            qTY = 1,
            rOOTPARTNUM = "43210M68PM0-QC8",
            sALESEFFDATE = "2021-09-15 00:00:00",
            tHUMBNAILIMAGEURL = "https://marutistoragenew1.azureedge.net/paccmicroservice/43210M68PM1-QC8/43210m68pm1-qc8_1_t.jpg",
            uNIT = "Number",
            uNITRATE = 10390.00
        )

        val l = listOf<ProductList>(
           sampleProduct
        )
        `when`(repository.getProducts()) doReturn flow {
            emit(Result.Loading(true))
            emit(Result.Loading(false))
            emit(Result.Success(l))
        }
        viewModel = MainViewModel(repository)
        viewModel.state.test {
            val first = awaitItem()
            assertEquals(first.isLoading,false)
            val second = awaitItem()
            assertEquals(second.isLoading,true)
            val third = awaitItem()
            assertEquals(third.isLoading,false)
            assert(awaitItem().data==l)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `repository returns loading then error`()= runTest{
        val msg = "Test Error"
        `when`(repository.getProducts()) doReturn flow {
            emit(Result.Loading(true))
            emit(Result.Loading(false))
            emit(Result.Error(msg))
        }
        viewModel = MainViewModel(repository)
        viewModel.state.test {
            val first = awaitItem()
            assertEquals(first.isLoading,false)
            val second = awaitItem()
            assertEquals(second.isLoading,true)
            val third = awaitItem()
            assertEquals(third.isLoading,false)
            val fourth = awaitItem()
            assert(fourth is Result.Error)
            assert(fourth.msg==msg)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}