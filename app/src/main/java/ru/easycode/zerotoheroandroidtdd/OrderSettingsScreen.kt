package ru.easycode.zerotoheroandroidtdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrderSettingsScreen(
    viewModel: ProductsViewModel,
    onNavigateBack: () -> Unit
) {
    val orders by viewModel.ordersUiListStateFlow.collectAsState()

    OrderSettingsScreenContent(
        orders = orders,
        onOrderSelected = { orderName ->
            viewModel.chooseOrder(orderName)
            onNavigateBack()
        }
    )
}

@Composable
private fun OrderSettingsScreenContent(
    orders: List<OrderUi>,
    onOrderSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Order By",
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(orders) { order ->
                Row(
                    modifier = Modifier
                        .selectable(selected = order.chosen, onClick = { onOrderSelected(order.name) })
                        .fillMaxWidth()
                        .testTag("Order option ${order.name}")
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = order.chosen, onCheckedChange = null)
                    Text(
                        text = order.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun OrderSettingsScreenPreview() {
    OrderSettingsScreenContent(
        orders = listOf(
            OrderUi(name = "price: low to high", chosen = true),
            OrderUi(name = "price: high to low", chosen = false),
        ),
        onOrderSelected = {}
    )
}
