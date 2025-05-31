package application.presentation.screens.store

import androidx.lifecycle.ViewModel
import application.domain.models.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

data class WishlistUiState(
    val items: List<WishlistItem> = emptyList()
)

@HiltViewModel
class WishlistViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState

    // Using a MutableList for in-memory storage
    private val wishlistItems = mutableListOf<WishlistItem>()

    fun addToWishlist(productName: String) {
        // For now, using productName as ID and name.
        // In a real app, you'd have a proper Product object.
        val newItem = WishlistItem(id = UUID.randomUUID().toString(), name = productName)
        if (!wishlistItems.any { it.name == newItem.name }) { // Avoid duplicates by name for this example
            wishlistItems.add(newItem)
            _uiState.value = WishlistUiState(items = ArrayList(wishlistItems)) // Create new list for StateFlow
        }
    }

    fun removeFromWishlist(item: WishlistItem) {
        wishlistItems.remove(item)
        _uiState.value = WishlistUiState(items = ArrayList(wishlistItems))
    }
}
