package application.domain.models

data class Product(
    val ID: String,
    val Name: String,
    val Barcode: String,
    val PurchasePrice: Double,
    val SalePrice: Double,
    val Category: String,
    val Stock: Int,
    val SupplierID: String
)
