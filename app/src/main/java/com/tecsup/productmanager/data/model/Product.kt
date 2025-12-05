package com.tecsup.productmanager.data.model

data class Product(
    var id: String = "",
    val userId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val category: String = ""
)