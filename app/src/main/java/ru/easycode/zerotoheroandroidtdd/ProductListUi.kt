package ru.easycode.zerotoheroandroidtdd

sealed interface ProductListUi {
    data class Base(
        val id: Int,
        val name: String,
        val price: String,
        val os: String,
        val ram: Int
    ) : ProductListUi

    fun Base.applyFilter(field: String, value: String): Boolean {
        return when (field.lowercase()) {
            "name" -> name
            "price" -> price
            "os" -> os
            "ram" -> ram
            else -> null
        }.toString() == value
    }

    data object Empty : ProductListUi
}