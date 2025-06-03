package application.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import application.data.local.entities.OrderDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {
    @Query("SELECT * FROM order_details WHERE orderId = :orderId")
    fun getOrderDetailsForOrder(orderId: String): Flow<List<OrderDetailEntity>>
    // Add other specific operations for order details if necessary in the future
    // e.g., getting details by product, etc.
}
