package com.saitejajanjirala.task_2.data.repo

import app.cash.turbine.test
import com.saitejajanjirala.task_2.data.remote.ApiService
import com.saitejajanjirala.task_2.di.NoInternetException
import com.saitejajanjirala.task_2.domain.models.Result
import com.saitejajanjirala.task_2.domain.remote.request.ProductsRequest
import com.saitejajanjirala.task_2.domain.remote.response.Data
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import com.saitejajanjirala.task_2.domain.remote.response.ProductResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepoImplTest {

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var productsRepoImpl: ProductsRepoImpl
    private lateinit var productsRequest: ProductsRequest
    private lateinit var productResult : ProductResult
    private lateinit var productList: List<ProductList>

    @Before
    fun setUp() {
        apiService = mock()
        Dispatchers.setMain(StandardTestDispatcher())
        productsRequest = ProductsRequest(
            listOf("AA"),
            1,
            10
        )
        val data  = ProductList(
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

        productList = listOf(
            data
        )

        productResult = ProductResult(
            data = Data(
                productList = productList
            )
        )
    }

    @Test
    fun `products repo returns success when api is SuccessFull`() = runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)
        `when`(apiService.geProductList(request = productsRequest)) doReturn Response.success(productResult)
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)
            val second = awaitItem()
            assertEquals(second.isLoading,false)
            val third = awaitItem()
            assert(third is Result.Success)
            third.data?.let {
                assert(it==productList)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `products repo returns error when data is empty`()= runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)
        val empty = emptyList<ProductList>()
        `when`(apiService.geProductList(request = productsRequest)) doReturn Response.success(
            ProductResult(
                data = Data(
                    productList = empty
                )
            )
        )
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)
            val second = awaitItem()
            assertEquals(second.isLoading,false)
            val third = awaitItem()
            assert(third is Result.Error)
            third.msg?.let {
                assert(it=="No Data Found")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `products repo returns error when data is null`()= runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)

        `when`(apiService.geProductList(request = productsRequest)) doReturn Response.success(
            null
        )
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)
            val second = awaitItem()
            assertEquals(second.isLoading,false)
            val third = awaitItem()
            assert(third is Result.Error)
            third.msg?.let {
                assert(it=="No Data Found")
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `products repo returns error when api call fails`()= runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)
        val m = "Unexpected Error occurred"
        `when`(apiService.geProductList(request = productsRequest)) doReturn Response.error(
            m.toResponseBody("text/plain".toMediaType()),
            okhttp3.Response.Builder()
                .request(
                    okhttp3.Request.Builder()
                        .url(ApiService.BASE_URL)
                        .build()
                )
                .protocol(okhttp3.Protocol.HTTP_1_1)
                .code(404)
                .message(m)
                .build()
        )
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)
            val second = awaitItem()
            assertEquals(second.isLoading,false)
            val third = awaitItem()
            assert(third is Result.Error)
            third.msg?.let {
                assert(it==m)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `products repo returns error when no Internet Connection `()= runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)
        val m = "Please Check your internet Connection"
        `when`(apiService.geProductList(request = productsRequest)).thenThrow(NoInternetException(m))
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)

            val third = awaitItem()
            assert(third is Result.Error)
            third.msg?.let {
                assert(it==m)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
    @Test
    fun `products repo returns error when apiservice throws exception `()= runTest{
        productsRepoImpl = ProductsRepoImpl(apiService)
        val m = "Unknown Error Occured"
        `when`(apiService.geProductList(request = productsRequest)).thenThrow(RuntimeException(m))
        productsRepoImpl.getProducts().test {
            val first = awaitItem()
            assertEquals(first.isLoading, true)

            val third = awaitItem()
            assert(third is Result.Error)
            third.msg?.let {
                assert(it==m)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }






    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}