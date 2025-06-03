package com.store20.data.local.dao // Updated package

import androidx.room.Dao
import androidx.room.Query
import com.store20.data.local.entities.OrderDetailEntity // Updated import
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {
    @Query("SELECT * FROM order_details WHERE orderId = :orderId")
    fun getOrderDetailsForOrder(orderId: String): Flow<List<OrderDetailEntity>>
    // Add other specific operations for order details if necessary in the future
    // e.g., getting details by product, etc.
}
