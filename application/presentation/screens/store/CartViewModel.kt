package application.presentation.screens.store

import androidx.lifecycle.ViewModel
import application.domain.models.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0
)

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val cartItems = mutableMapOf<String, CartItem>() // Use Map for easier quantity updates by product ID/name

    fun addToCart(productName: String, productPrice: Double = 10.0) { // Default price for example
        val existingItem = cartItems[productName] // Assuming productName is unique ID for now
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            // For now, using productName as ID. In a real app, you'd have a proper Product object.
            cartItems[productName] = CartItem(id = UUID.randomUUID().toString(), name = productName, quantity = 1, price = productPrice)
        }
        updateUiState()
    }

    fun removeFromCart(item: CartItem) {
        cartItems.remove(item.name) // Assuming name is the key used
        updateUiState()
    }

    fun increaseQuantity(item: CartItem) {
        cartItems[item.name]?.quantity?.let {
            cartItems[item.name]?.quantity = it + 1
        }
        updateUiState()
    }

    fun decreaseQuantity(item: CartItem) {
        cartItems[item.name]?.quantity?.let {
            if (it > 1) {
                cartItems[item.name]?.quantity = it - 1
            } else {
                // If quantity becomes 0, remove the item
                cartItems.remove(item.name)
            }
        }
        updateUiState()
    }

    private fun updateUiState() {
        val currentItems = ArrayList(cartItems.values)
        val currentTotalPrice = currentItems.sumOf { it.getTotalPrice() }
        _uiState.value = CartUiState(items = currentItems, totalPrice = currentTotalPrice)
    }
}
