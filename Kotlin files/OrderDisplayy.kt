package com.alikhan.projecttrial

data class OrderDisplayy(
    val orderId: String,
    val delivered: String,
    val date: String,
    val price: String,
    val imageResource: Int // Assuming imageResource is the drawable resource ID
)