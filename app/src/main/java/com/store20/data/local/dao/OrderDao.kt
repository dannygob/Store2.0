package com.store20.data.local.dao // Updated package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.store20.data.local.entities.OrderEntity // Updated import
import com.store20.data.local.entities.OrderDetailEntity // Updated import
import com.store20.data.local.relations.OrderWithDetails // Updated import
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetails(orderDetails: List<OrderDetailEntity>)

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun getOrderById(orderId: String): Flow<OrderEntity?>

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE customerId = :customerId ORDER BY orderDate DESC")
    fun getOrdersForCustomer(customerId: String): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun getOrderWithDetails(orderId: String): Flow<OrderWithDetails?>
}
