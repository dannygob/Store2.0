package com.store20.domain.models // Updated package

data class OrderDetail(
    val orderDetailId: String,
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val priceAtPurchase: Double,
    val productName: String? = null // To be populated via lookup later
)
