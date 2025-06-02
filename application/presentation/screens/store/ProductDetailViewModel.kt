package application.presentation.screens.store

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import application.data.remote.api.FakeStoreApiService
import application.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val fakeStoreApiService: FakeStoreApiService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val productId: Int? = savedStateHandle.get<String>("productId")?.toIntOrNull()

    init {
        if (productId != null) {
            fetchProductDetails(productId)
        } else {
            _uiState.value = ProductDetailUiState(error = "Product ID not found.")
        }
    }

    fun fetchProductDetails(id: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val product = fakeStoreApiService.getProductById(id)
                _uiState.value = _uiState.value.copy(isLoading = false, product = product)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Failed to fetch product details.")
            }
        }
    }

    fun errorShown() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    // Methods for AddToCart/AddToWishlist will not be here.
    // The screen will call CartViewModel/WishlistViewModel directly.
}
