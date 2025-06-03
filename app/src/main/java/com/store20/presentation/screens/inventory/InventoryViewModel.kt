package com.store20.presentation.screens.inventory // Updated package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store20.domain.models.Product // Updated import
import com.store20.domain.repository.ProductRepository // Updated import
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
    private val productRepository: ProductRepository
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
                        println("InventoryViewModel: Failed to sync deletion of product $productId to Firebase. Error: ${firebaseDeleteResult.exceptionOrNull()?.message}")
                        // Optionally emit to _errorEvents, but be cautious about error message priority
                    }
                } else {
                    _errorEvents.emit("Product not found.")
                }
            } catch (e: Exception) {
                // This catch block now primarily handles errors from getProductById or local deleteProduct
                _errorEvents.emit("Error during local product operation: ${e.message}")
            }
        }
    }
}
