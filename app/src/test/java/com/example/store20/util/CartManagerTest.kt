package com.example.store20.util

import com.example.store20.model.Product
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartManagerTest {

    // Dummy products for testing
    private val product1 = Product("P1", "Product 1", "Desc 1", 10.00, "img1.url")
    private val product2 = Product("P2", "Product 2", "Desc 2", 20.50, "img2.url")
    private val product3 = Product("P3", "Product 3", "Desc 3", 5.75, "img3.url")

    @Before
    fun setUp() {
        // Reset CartManager state before each test
        CartManager.clearCartForTest()
        CartManager.clearOrderHistoryForTest()
    }

    @After
    fun tearDown() {
        // Optional: could also clear here if preferred, or if a test might leave state on failure
        CartManager.clearCartForTest()
        CartManager.clearOrderHistoryForTest()
    }

    // --- addItem() Tests ---
    @Test
    fun `addItem newProduct cartUpdated`() {
        CartManager.addItem(product1)
        assertEquals(1, CartManager.getCartItems().size)
        assertEquals(product1.id, CartManager.getCartItems()[0].product.id)
        assertEquals(1, CartManager.getCartItems()[0].quantity)
    }

    @Test
    fun `addItem existingProduct quantityIncreased`() {
        CartManager.addItem(product1) // Add once
        CartManager.addItem(product1) // Add again
        assertEquals(1, CartManager.getCartItems().size) // Should still be one item entry
        assertEquals(2, CartManager.getCartItems()[0].quantity) // Quantity should be 2
    }

    @Test
    fun `addItem multipleDifferentProducts cartUpdated`() {
        CartManager.addItem(product1)
        CartManager.addItem(product2)
        assertEquals(2, CartManager.getCartItems().size)
    }


    // --- removeItemByProductId() Tests (preferred over direct CartItem removal for testing) ---
    @Test
    fun `removeItemByProductId itemExists removedFromCart`() {
        CartManager.addItem(product1)
        CartManager.addItem(product2)
        CartManager.removeItemByProductId(product1.id)
        assertEquals(1, CartManager.getCartItems().size)
        assertEquals(product2.id, CartManager.getCartItems()[0].product.id)
    }

    @Test
    fun `removeItemByProductId itemNotExists cartUnchanged`() {
        CartManager.addItem(product1)
        CartManager.addItem(product2)
        CartManager.removeItemByProductId("P_NON_EXISTENT") // Non-existent ID
        assertEquals(2, CartManager.getCartItems().size)
    }

    @Test
    fun `removeItemByProductId lastItem cartIsEmpty`() {
        CartManager.addItem(product1)
        CartManager.removeItemByProductId(product1.id)
        assertTrue(CartManager.getCartItems().isEmpty())
    }


    // --- updateQuantity() Tests ---
    @Test
    fun `updateQuantity increaseQuantity quantityUpdated`() {
        CartManager.addItem(product1)
        CartManager.updateQuantity(product1.id, 3)
        assertEquals(3, CartManager.getCartItems()[0].quantity)
    }

    @Test
    fun `updateQuantity decreaseQuantity quantityUpdated`() {
        CartManager.addItem(product1) // quantity is 1
        CartManager.updateQuantity(product1.id, 2) // make it 2
        CartManager.updateQuantity(product1.id, 1) // decrease to 1
        assertEquals(1, CartManager.getCartItems()[0].quantity)
    }

    @Test
    fun `updateQuantity decreaseToZero itemRemoved`() {
        CartManager.addItem(product1)
        CartManager.updateQuantity(product1.id, 0)
        assertTrue(CartManager.getCartItems().isEmpty())
    }

    @Test
    fun `updateQuantity itemNotExists cartUnchanged`() {
        CartManager.addItem(product1)
        CartManager.updateQuantity("P_NON_EXISTENT", 2)
        assertEquals(1, CartManager.getCartItems().size)
        assertEquals(1, CartManager.getCartItems()[0].quantity)
    }

    @Test
    fun `updateQuantity decreaseBelowZero itemRemoved`() {
        CartManager.addItem(product1)
        CartManager.updateQuantity(product1.id, -1)
        assertTrue(CartManager.getCartItems().isEmpty())
    }


    // --- getTotalPrice() Tests ---
    @Test
    fun `totalPrice emptyCart isZero`() {
        assertEquals(0.0, CartManager.getTotalPrice(), 0.001)
    }

    @Test
    fun `totalPrice withSingleItem calculatesCorrectly`() {
        CartManager.addItem(product1) // Price 10.0
        assertEquals(10.0, CartManager.getTotalPrice(), 0.001)
    }

    @Test
    fun `totalPrice withMultipleItemsAndQuantities calculatesCorrectly`() {
        CartManager.addItem(product1) // 10.0
        CartManager.addItem(product1) // 10.0 (qty 2 now) -> 20.0
        CartManager.addItem(product2) // 20.5
        // Total = 10.0 * 2 + 20.5 = 20.0 + 20.5 = 40.5
        assertEquals(40.5, CartManager.getTotalPrice(), 0.001)
    }

    @Test
    fun `totalPrice afterRemovingItem calculatesCorrectly`() {
        CartManager.addItem(product1) // 10.0
        CartManager.addItem(product2) // 20.5
        CartManager.removeItemByProductId(product1.id)
        assertEquals(20.5, CartManager.getTotalPrice(), 0.001)
    }


    // --- getCartItemCount() Tests ---
    @Test
    fun `itemCount emptyCart isZero`() {
        assertEquals(0, CartManager.getCartItemCount())
    }

    @Test
    fun `itemCount withSingleItemIsCorrect`() {
        CartManager.addItem(product1)
        assertEquals(1, CartManager.getCartItemCount())
    }

    @Test
    fun `itemCount withMultipleItemsAndQuantitiesIsCorrect`() {
        CartManager.addItem(product1) // qty 1
        CartManager.addItem(product1) // qty 2
        CartManager.addItem(product2) // qty 1
        // Total items = 2 (for P1) + 1 (for P2) = 3
        assertEquals(3, CartManager.getCartItemCount())
    }

    @Test
    fun `itemCount afterRemovingItemIsCorrect`() {
        CartManager.addItem(product1)
        CartManager.addItem(product2) // P1 qty 1, P2 qty 1. Total 2
        CartManager.removeItemByProductId(product1.id)
        assertEquals(1, CartManager.getCartItemCount()) // Only P2 qty 1 left
    }


    // --- placeOrderAndClearCart() Tests ---
    @Test
    fun `placeOrder cartNotEmpty orderCreatedAndCartCleared`() {
        CartManager.addItem(product1)
        CartManager.addItem(product2)
        val initialCartSize = CartManager.getCartItems().size
        assertTrue(initialCartSize > 0)

        val order = CartManager.placeOrderAndClearCart()
        assertNotNull(order)
        assertTrue(CartManager.getCartItems().isEmpty()) // Cart should be empty
        assertEquals(1, CartManager.getOrderHistory().size) // One order in history
    }

    @Test
    fun `placeOrder orderContainsCorrectItemsAndTotal`() {
        CartManager.addItem(product1) // 10.0
        CartManager.updateQuantity(product1.id, 2) // 2 * 10.0 = 20.0
        CartManager.addItem(product2) // 20.5
        // Expected total: 40.5. Expected item details: P1 qty 2, P2 qty 1.

        val order = CartManager.placeOrderAndClearCart()
        assertNotNull(order)
        assertEquals(40.5, order!!.totalAmount, 0.001)
        assertEquals(2, order.items.size) // Two distinct products

        val orderItem1 = order.items.find { it.product.id == product1.id }
        assertNotNull(orderItem1)
        assertEquals(2, orderItem1!!.quantity)

        val orderItem2 = order.items.find { it.product.id == product2.id }
        assertNotNull(orderItem2)
        assertEquals(1, orderItem2!!.quantity)
    }

    @Test
    fun `placeOrder emptyCart noOrderCreatedAndCartRemainsEmpty`() {
        assertTrue(CartManager.getCartItems().isEmpty())
        val order = CartManager.placeOrderAndClearCart()
        assertNull(order)
        assertTrue(CartManager.getCartItems().isEmpty())
        assertTrue(CartManager.getOrderHistory().isEmpty())
    }

    // --- getOrderHistory() Tests ---
    @Test
    fun `getOrderHistory noOrders isEmpty`() {
        assertTrue(CartManager.getOrderHistory().isEmpty())
    }

    @Test
    fun `getOrderHistory afterPlacingOrders returnsOrders`() {
        CartManager.addItem(product1)
        CartManager.placeOrderAndClearCart() // Order 1

        CartManager.addItem(product2)
        CartManager.addItem(product3)
        CartManager.placeOrderAndClearCart() // Order 2

        val orderHistory = CartManager.getOrderHistory()
        assertEquals(2, orderHistory.size)
        assertEquals(product1.id, orderHistory[0].items[0].product.id)
        assertEquals(product2.id, orderHistory[1].items.find { it.product.id == product2.id }?.product?.id)
        assertEquals(product3.id, orderHistory[1].items.find { it.product.id == product3.id }?.product?.id)
    }
}
