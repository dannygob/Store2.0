package com.example.store20.model

data class Order(
    val id: String,
    val date: Long, // Timestamp
    val items: List<CartItem>,
    val totalAmount: Double
)
