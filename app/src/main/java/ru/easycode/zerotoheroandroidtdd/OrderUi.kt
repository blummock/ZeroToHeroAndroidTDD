package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

data class OrderUi(
    val name: String,
    val chosen: Boolean
) : Serializable

fun OrderUi.createComparator() = when (name) {
    "price: low to high" -> compareBy<ProductListUi.Base> { it.price }
    "price: high to low" -> compareByDescending { it.price }
    "alphabet" -> compareBy { it.name }
    else -> compareBy { it.id }
}