package ru.easycode.zerotoheroandroidtdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onOpenOrderSettings: () -> Unit,
    onOpenFilterSettings: () -> Unit
) {
    val products by viewModel.productsUiListStateFlow.collectAsState()
    ProductsContent(
        products = products,
        onOpenOrderSettings = onOpenOrderSettings,
        onOpenFilterSettings = onOpenFilterSettings
    )
}

@Composable
private fun ProductsContent(
    products: List<ProductListUi>,
    onOpenOrderSettings: () -> Unit,
    onOpenFilterSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onOpenOrderSettings,
                modifier = Modifier
                    .weight(1f)
                    .testTag("order button")
            ) {
                Text(text = "order")
            }
            Button(
                onClick = onOpenFilterSettings,
                modifier = Modifier
                    .weight(1f)
                    .testTag("filters button")
                    .padding(start = 8.dp)
            ) {
                Text(text = "filters")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp)
                .testTag("ProductsLazyColumn")
        ) {
            itemsIndexed(products) { index, product ->
                when (product) {
                    is ProductListUi.Base -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .testTag("Product at $index")
                        ) {
                            Text(text = product.name, modifier = Modifier.testTag("Product name at $index"))
                            Text(text = product.price, modifier = Modifier.testTag("Product price at $index"))
                            Text(text = product.os, modifier = Modifier.testTag("Product os at $index"))
                            Text(text = product.ram.toString(), modifier = Modifier.testTag("Product ram at $index"))
                        }
                    }

                    ProductListUi.Empty -> {
                        Text(
                            text = "No products found",
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("nothing found")
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductsScreen() {
    ZeroToHeroAndroidTDDTheme {
        ProductsContent(
            products = listOf(
                ProductListUi.Base(
                    id = 1,
                    name = "Device A",
                    price = "300$",
                    os = "Android",
                    ram = 6
                ),
                ProductListUi.Base(
                    id = 1,
                    name = "Device B",
                    price = "100$",
                    os = "Android",
                    ram = 8
                )
            ), onOpenOrderSettings = {}) { }
    }
}
