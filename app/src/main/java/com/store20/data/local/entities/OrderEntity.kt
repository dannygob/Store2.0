package com.store20.data.local.entities // Updated package

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
// No need to update CustomerEntity import if it's in the same package after move.
// If it were in a different sub-package, then the import would be e.g. com.store20.data.local.entities.customers.CustomerEntity

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class, // This should resolve to the new package if in same dir
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.SET_NULL // Or another action like RESTRICT, CASCADE
        )
    ]
)
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val customerId: String?, // Nullable for anonymous sales
    val orderDate: Long,
    val totalAmount: Double
)
