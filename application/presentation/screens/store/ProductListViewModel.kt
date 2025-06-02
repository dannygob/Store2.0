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

data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null,
    val categoryName: String = ""
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val fakeStoreApiService: FakeStoreApiService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val categoryName: String? = savedStateHandle["categoryName"]

    init {
        if (categoryName != null) {
            _uiState.value = _uiState.value.copy(categoryName = categoryName.replaceFirstChar { it.uppercase() })
            fetchProducts(categoryName)
        } else {
            _uiState.value = _uiState.value.copy(error = "Category not specified.")
        }
    }

    fun fetchProducts(category: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val products = fakeStoreApiService.getProductsByCategory(category)
                _uiState.value = _uiState.value.copy(isLoading = false, products = products)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Failed to fetch products.")
            }
        }
    }

    fun errorShown() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
