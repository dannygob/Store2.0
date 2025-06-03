package com.store20.presentation.screens.inventory // Updated package

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.store20.presentation.navigation.AppDestinations // Updated import
import com.store20.domain.models.Product // Updated import
import com.store20.domain.repository.ProductRepository // Updated import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class AddProductUiState(
    val nameError: String? = null,
    val purchasePriceError: String? = null,
    val salePriceError: String? = null,
    val stockError: String? = null,
    val categoryError: String? = null,
    val supplierIdError: String? = null,
    val submissionSuccess: Boolean = false,
    val submissionError: String? = null // General error message
)

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    private val _productId = MutableStateFlow(UUID.randomUUID().toString())
    val productId: StateFlow<String> = _productId.asStateFlow()

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _barcode = MutableStateFlow("")
    val barcode: StateFlow<String> = _barcode.asStateFlow()

    private val _purchasePrice = MutableStateFlow("")
    val purchasePrice: StateFlow<String> = _purchasePrice.asStateFlow()

    private val _salePrice = MutableStateFlow("")
    val salePrice: StateFlow<String> = _salePrice.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _stock = MutableStateFlow("")
    val stock: StateFlow<String> = _stock.asStateFlow()

    private val _supplierId = MutableStateFlow("")
    val supplierId: StateFlow<String> = _supplierId.asStateFlow()

    init {
        val navProductId: String? = savedStateHandle[AppDestinations.PRODUCT_ID_ARG]
        if (navProductId != null) {
            _isEditMode.value = true
            _productId.value = navProductId
            viewModelScope.launch {
                try {
                    val product = productRepository.getProductById(navProductId)
                    if (product != null) {
                        _name.value = product.Name
                        _barcode.value = product.Barcode ?: ""
                        _purchasePrice.value = product.PurchasePrice.toString()
                        _salePrice.value = product.SalePrice.toString()
                        _category.value = product.Category
                        _stock.value = product.Stock.toString()
                        _supplierId.value = product.SupplierID
                    } else {
                        _uiState.value = _uiState.value.copy(submissionError = "Product not found.")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(submissionError = "Error loading product data: ${e.message}")
                }
            }
        } else {
            _isEditMode.value = false
        }
    }

    fun onNameChange(newName: String) { _name.value = newName }
    fun onBarcodeChange(newBarcode: String) { _barcode.value = newBarcode }
    fun onPurchasePriceChange(newPrice: String) { _purchasePrice.value = newPrice }
    fun onSalePriceChange(newPrice: String) { _salePrice.value = newPrice }
    fun onCategoryChange(newCategory: String) { _category.value = newCategory }
    fun onStockChange(newStock: String) { _stock.value = newStock }
    fun onSupplierIdChange(newId: String) { _supplierId.value = newId }


    fun saveProduct() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(submissionSuccess = false, submissionError = null)

            var currentErrors = AddProductUiState()
            var hasError = false

            if (_name.value.isBlank()) {
                currentErrors = currentErrors.copy(nameError = "Name cannot be empty")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(nameError = null)
            }

            val purchasePriceVal = _purchasePrice.value.toDoubleOrNull()
            if (purchasePriceVal == null || purchasePriceVal < 0) {
                currentErrors = currentErrors.copy(purchasePriceError = "Invalid purchase price")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(purchasePriceError = null)
            }

            val salePriceVal = _salePrice.value.toDoubleOrNull()
            if (salePriceVal == null || salePriceVal < 0) {
                currentErrors = currentErrors.copy(salePriceError = "Invalid sale price")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(salePriceError = null)
            }

            val stockVal = _stock.value.toIntOrNull()
            if (stockVal == null || stockVal < 0) {
                currentErrors = currentErrors.copy(stockError = "Invalid stock quantity")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(stockError = null)
            }

            if (_category.value.isBlank()) {
                currentErrors = currentErrors.copy(categoryError = "Category cannot be empty")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(categoryError = null)
            }

            if (_supplierId.value.isBlank()) {
                currentErrors = currentErrors.copy(supplierIdError = "Supplier ID cannot be empty")
                hasError = true
            } else {
                currentErrors = currentErrors.copy(supplierIdError = null)
            }
            _uiState.value = _uiState.value.copy(
                nameError = currentErrors.nameError,
                purchasePriceError = currentErrors.purchasePriceError,
                salePriceError = currentErrors.salePriceError,
                stockError = currentErrors.stockError,
                categoryError = currentErrors.categoryError,
                supplierIdError = currentErrors.supplierIdError
            )

            if (hasError) {
                return@launch
            }

            val product = Product(
                ID = _productId.value,
                Name = _name.value,
                Barcode = _barcode.value,
                PurchasePrice = purchasePriceVal!!,
                SalePrice = salePriceVal!!,
                Category = _category.value,
                Stock = stockVal!!,
                SupplierID = _supplierId.value
            )

            try {
                val productToSave = product
                if (_isEditMode.value) {
                    productRepository.updateProduct(productToSave)
                } else {
                    productRepository.insertProduct(productToSave)
                }

                val syncResult = productRepository.syncProductToFirebase(productToSave)
                if (syncResult.isSuccess) {
                    println("AddProductViewModel: Product ${productToSave.ID} synced to Firebase successfully.")
                } else {
                    println("AddProductViewModel: Failed to sync product ${productToSave.ID} to Firebase. Error: ${syncResult.exceptionOrNull()?.message}")
                }

                _uiState.value = _uiState.value.copy(
                    submissionSuccess = true,
                    submissionError = null,
                    nameError = null,
                    purchasePriceError = null,
                    salePriceError = null,
                    stockError = null,
                    categoryError = null,
                    supplierIdError = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(submissionSuccess = false, submissionError = "An unexpected error occurred while saving: ${e.message}")
            }
        }
    }

    fun dismissSubmissionStatus() {
        _uiState.value = _uiState.value.copy(submissionSuccess = false, submissionError = null)
    }
}
