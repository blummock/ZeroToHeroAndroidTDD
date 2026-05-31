package ru.easycode.zerotoheroandroidtdd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.io.Serializable

@Composable
fun MainScreen(viewModel: ProductsViewModel) {
    var destination by rememberSaveable { mutableStateOf<Dest>(Dest.Products) }
    when (destination) {
        is Dest.Products -> {
            ProductsScreen(
                viewModel = viewModel,
                onOpenOrderSettings = { destination = Dest.Orders },
                onOpenFilterSettings = { destination = Dest.Filters }
            )
        }

        is Dest.Orders -> {
            OrderSettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { destination = Dest.Products }
            )
        }

        is Dest.Filters -> {
            FilterSettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { destination = Dest.Products }
            )
        }
    }
}

private interface Dest : Serializable {

    data object Products : Dest
    data object Orders : Dest
    data object Filters : Dest
}