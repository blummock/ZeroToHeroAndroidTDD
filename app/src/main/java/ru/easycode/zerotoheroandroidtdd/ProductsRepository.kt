package ru.easycode.zerotoheroandroidtdd

interface ProductsRepository {
    suspend fun products(): List<Product>
    suspend fun orderList(): List<String>
    suspend fun filters(): List<ProductFilter>

    class Base() : ProductsRepository {
        override suspend fun products() = listOf(
            Product(id = 1, name = "Device A", price = "300$", os = "Android", ram = 6),
            Product(id = 2, name = "Device B", price = "400$", os = "iOS", ram = 6),
            Product(id = 3, name = "Device C", price = "200$", os = "Android", ram = 4),
            Product(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8),
        )

        override suspend fun orderList() = listOf(
            "alphabet", "price: low to high", "price: high to low"
        )

        override suspend fun filters() = listOf(
            ProductFilter(id = 10, category = "os", name = "Android"),
            ProductFilter(id = 11, category = "os", name = "iOS"),
            ProductFilter(id = 20, category = "RAM", name = "4"),
            ProductFilter(id = 21, category = "RAM", name = "6"),
            ProductFilter(id = 22, category = "RAM", name = "8"),
        )
    }
}