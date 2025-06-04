package application.domain.models

data class Product(
    val id: String,       // Was ID
    val name: String,     // Was Name
    val barcode: String,  // Was Barcode
    val purchasePrice: Double, // Was PurchasePrice
    val salePrice: Double,   // Was SalePrice
    val category: String,   // Was Category
    val stock: Int,       // Was Stock
    val supplierId: String // Was SupplierID
)
