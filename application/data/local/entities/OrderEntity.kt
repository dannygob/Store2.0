package application.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class,
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
