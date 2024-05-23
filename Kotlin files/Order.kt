package com.fahad.smd_project
// Data class representing an order

data class Order(
    val orderId: String = "",
    val total: Double = 0.0,
    val selectedDate: String
)