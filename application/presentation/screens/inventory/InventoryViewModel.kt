package application.presentation.screens.inventory

import android.app.Application // Added Application import
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import application.R // Added R import
import application.domain.models.Product
import application.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val app: Application // Added Application context
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            // Consider adding try-catch here as well if getAllProducts can fail
            productRepository.getAllProducts().collectLatest { productList ->
                _products.value = productList
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId)
                if (product != null) {
                    productRepository.deleteProduct(product) // Local deletion
                    println("InventoryViewModel: Product $productId deleted locally.") // Optional log

                    // Delete from Firebase
                    val firebaseDeleteResult = productRepository.deleteProductFromFirebase(productId)
                    if (firebaseDeleteResult.isSuccess) {
                        println("InventoryViewModel: Product $productId deletion synced to Firebase.")
                    } else {
                        val errorMessage = firebaseDeleteResult.exceptionOrNull()?.message ?: "Unknown error"
                        println("InventoryViewModel: Failed to sync deletion of product $productId to Firebase. Error: $errorMessage")
                        // Emit error to UI
                        _errorEvents.emit(app.getString(R.string.error_firebase_delete_failed, productId))
                    }
                } else {
                    _errorEvents.emit(app.getString(R.string.error_product_not_found))
                }
            } catch (e: Exception) {
                // This catch block now primarily handles errors from getProductById or local deleteProduct
                _errorEvents.emit(app.getString(R.string.error_local_product_operation, e.message ?: "Unknown error"))
            }
        }
    }
}
