package application.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = SupplierEntity::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["supplier_id"])]
)
data class ProductEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "barcode") val barcode: String,
    @ColumnInfo(name = "purchase_price") val purchasePrice: Double,
    @ColumnInfo(name = "sale_price") val salePrice: Double,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "stock") val stock: Int,
    @ColumnInfo(name = "supplier_id") val supplierId: String
)
