package application.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val ID: String,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "barcode") val Barcode: String,
    @ColumnInfo(name = "purchase_price") val PurchasePrice: Double,
    @ColumnInfo(name = "sale_price") val SalePrice: Double,
    @ColumnInfo(name = "category") val Category: String,
    @ColumnInfo(name = "stock") val Stock: Int,
    @ColumnInfo(name = "supplier_id") val SupplierID: String
)
