package com.saitejajanjirala.task_2.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.saitejajanjirala.task_2.domain.models.Result
import com.saitejajanjirala.task_2.domain.remote.response.ProductList
import com.saitejajanjirala.task_2.ui.theme.Task2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        val state by viewModel.state.collectAsState()
                        when(state){
                            is Result.Error -> {
                                Text(text = state.msg?:"Unknown Error",
                                    style = MaterialTheme.typography.titleMedium)
                            }
                            is Result.Loading -> {
                                if(state.isLoading){
                                    CircularProgressIndicator()
                                }
                            }
                            is Result.Success -> {
                                LazyColumn {
                                    state.data?.let {
                                        items(it){
                                            ProductListItem(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductListItem(
    product: ProductList,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = if (product.oUTOFSTOCK == "Y") Color.LightGray else Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.tHUMBNAILIMAGEURL,
            contentDescription = product.pARTNAME,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.pARTNAME ?: "Unknown Product",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Part Number: ${product.pARTNUM ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "₹${product.pARTMRP?.formatPrice() ?: "0.00"}",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        if (product.oUTOFSTOCK == "Y") {
            Text(
                text = "Out of Stock",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

fun Double.formatPrice(): String {
    return String.format("%.2f", this)
}
