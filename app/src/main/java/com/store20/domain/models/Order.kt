package com.store20.domain.models // Updated package

// Assuming OrderDetail is in the same package: com.store20.domain.models

data class Order(
    val orderId: String,
    val customerId: String?,
    val orderDate: Long,
    val totalAmount: Double,
    val items: List<OrderDetail>
)
