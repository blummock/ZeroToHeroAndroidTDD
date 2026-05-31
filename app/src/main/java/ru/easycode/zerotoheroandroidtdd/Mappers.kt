package ru.easycode.zerotoheroandroidtdd

fun Product.mapToUi() = ProductListUi.Base(
    id = this.id,
    name = this.name,
    price = this.price,
    os = this.os,
    ram = this.ram
)

fun String.mapToUi(chosen: Boolean) = OrderUi(
    name = this,
    chosen = chosen
)

fun ProductFilter.mapToUi(chosen: Boolean) = FilterUi(
    id = this.id,
    category = this.category,
    value = this.name,
    chosen = chosen,
)


