package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import ru.easycode.zerotoheroandroidtdd.ProductListUi.Empty.applyFilter

class ProductsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductsRepository,
    runAsync: RunAsync,
) : ViewModel() {

    private val _productsUiListStateFlow = MutableStateFlow<List<ProductListUi>>(emptyList())
    val productsUiListStateFlow: StateFlow<List<ProductListUi>> = _productsUiListStateFlow.asStateFlow()

    private val _ordersUiListStateFlow = MutableStateFlow<List<OrderUi>>(emptyList())
    val ordersUiListStateFlow: StateFlow<List<OrderUi>> = _ordersUiListStateFlow.asStateFlow()

    private val _filtersUiListStateFlow = MutableStateFlow<List<FilterUi>>(emptyList())
    val filtersUiListStateFlow: StateFlow<List<FilterUi>> = _filtersUiListStateFlow.asStateFlow()

    private val selectedOrder = MutableStateFlow(0)

    private val productsCombined = combine(
        flow { emit(repository.products().map { it.mapToUi() }) },
        ordersUiListStateFlow,
        filtersUiListStateFlow
    ) { products, orders, filters ->
        products
            .filter { product ->
                filters.filter { it.chosen }.all { product.applyFilter(it.category, it.value) }
            }
            .sortedWith(orders.firstOrNull { it.chosen }?.createComparator() ?: compareBy { it.id })
    }

    init {
        runAsync.runFlowCollect(
            scope = viewModelScope,
            flow = productsCombined,
        ) { products ->
            _productsUiListStateFlow.value = products.ifEmpty { listOf(ProductListUi.Empty) }
        }
        runAsync.runFlowCollect(
            scope = viewModelScope,
            flow = flow { emit(repository.orderList()) }.combine(selectedOrder) { orders, selected ->
                orders.mapIndexed { index, string -> string.mapToUi(index == selected) }
            }
        ) { orders ->
            _ordersUiListStateFlow.value = orders
        }
        runAsync.runFlowCollect(
            scope = viewModelScope,
            flow = flow<List<ProductFilter>> { emit(repository.filters()) }
        ) { names ->
            _filtersUiListStateFlow.value = names.map { filter -> filter.mapToUi(false) }
        }
    }

    fun chooseOrder(name: String) {
        selectedOrder.value = _ordersUiListStateFlow.value.indexOfFirst { it.name == name }
    }

    fun chooseFilter(id: Int) {
        val category = _filtersUiListStateFlow.value.find { it.id == id }?.category ?: return
        _filtersUiListStateFlow.update { filters ->
            filters.map {
                if (it.id == id) {
                    it.copy(chosen = !it.chosen)
                } else if (it.category == category) {
                    it.copy(chosen = false)
                } else it
            }
        }
    }

    fun unchooseFilter(id: Int) {
        chooseFilter(id)
    }
}
