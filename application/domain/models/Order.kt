package application.domain.models

data class Order(
    val orderId: String,
    val customerId: String?,
    val orderDate: Long,
    val totalAmount: Double,
    val items: List<OrderDetail>
)
