package application.presentation.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import application.domain.models.Product
import application.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _addProductResult = MutableSharedFlow<Boolean>()
    val addProductResult = _addProductResult.asSharedFlow()

    fun addProduct(
        id: String,
        name: String,
        barcode: String,
        purchasePriceStr: String,
        salePriceStr: String,
        category: String,
        stockStr: String,
        supplierId: String
    ) {
        viewModelScope.launch {
            if (id.isBlank() || name.isBlank() || category.isBlank() || supplierId.isBlank()) {
                _addProductResult.emit(false)
                return@launch
            }

            val purchasePrice = purchasePriceStr.toDoubleOrNull()
            val salePrice = salePriceStr.toDoubleOrNull()
            val stock = stockStr.toIntOrNull()

            if (purchasePrice == null || salePrice == null || stock == null || purchasePrice < 0 || salePrice < 0 || stock < 0) {
                _addProductResult.emit(false)
                return@launch
            }

            val product = Product(
                ID = id,
                Name = name,
                Barcode = barcode, // Barcode can be blank
                PurchasePrice = purchasePrice,
                SalePrice = salePrice,
                Category = category,
                Stock = stock,
                SupplierID = supplierId
            )
            productRepository.insertProduct(product)
            _addProductResult.emit(true)
        }
    }
}
