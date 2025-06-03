package com.store20.data.local.entities // Updated package

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Assuming OrderEntity and ProductEntity are in the same package: com.store20.data.local.entities

@Entity(
    tableName = "order_details",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["orderId"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE // If an order is deleted, its details should also be deleted
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["ID"], // Assuming 'ID' is the PrimaryKey in ProductEntity
            childColumns = ["productId"],
            onDelete = ForeignKey.RESTRICT // Prevent deleting a product if it's part of an order
        )
    ],
    indices = [Index("orderId"), Index("productId")]
)
data class OrderDetailEntity(
    @PrimaryKey val orderDetailId: String,
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val priceAtPurchase: Double
)
