package com.store20.data.local.relations // Updated package

import androidx.room.Embedded
import androidx.room.Relation
import com.store20.data.local.entities.OrderEntity // Updated import
import com.store20.data.local.entities.OrderDetailEntity // Updated import

data class OrderWithDetails(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val details: List<OrderDetailEntity>
)
