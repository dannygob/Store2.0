package application.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import application.data.local.entities.OrderEntity
import application.data.local.entities.OrderDetailEntity

data class OrderWithDetails(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val details: List<OrderDetailEntity>
)
