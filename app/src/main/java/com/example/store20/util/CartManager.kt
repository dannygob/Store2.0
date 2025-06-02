package com.example.store20.util

import com.example.store20.model.CartItem
import com.example.store20.model.Order
import com.example.store20.model.Product
import java.util.UUID

object CartManager {
    private val items = mutableListOf<CartItem>()
    private val orderHistory = mutableListOf<Order>()

    fun addItem(product: Product) {
        val existingItem = items.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            items.add(CartItem(product, 1))
        }
    }

    fun removeItem(cartItem: CartItem) {
        items.remove(cartItem)
    }

    fun removeItemByProductId(productId: String) {
        items.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        val item = items.find { it.product.id == productId }
        item?.let {
            if (newQuantity > 0) {
                it.quantity = newQuantity
            } else {
                items.remove(it)
            }
        }
    }

    fun getCartItems(): List<CartItem> {
        return items.toList() // Return a copy to prevent external modification
    }

    fun getTotalPrice(): Double {
        return items.sumOf { it.product.price * it.quantity }
    }

    fun getCartItemCount(): Int {
        return items.sumOf { it.quantity }
    }

    // Renamed from clearCart and modified
    fun placeOrderAndClearCart(): Order? {
        if (items.isEmpty()) return null

        val currentItemsCopy = items.toList() // Create a copy for the order
        val totalAmount = getTotalPrice()
        val newOrder = Order(
            id = UUID.randomUUID().toString(),
            date = System.currentTimeMillis(),
            items = currentItemsCopy,
            totalAmount = totalAmount
        )
        orderHistory.add(newOrder)
        items.clear() // Clear the current cart
        return newOrder
    }

    fun getOrderHistory(): List<Order> {
        return orderHistory.toList() // Return a copy
    }

    // --- Testability Methods ---
    internal fun clearCartForTest() {
        items.clear()
    }

    internal fun clearOrderHistoryForTest() {
        orderHistory.clear()
    }
}
