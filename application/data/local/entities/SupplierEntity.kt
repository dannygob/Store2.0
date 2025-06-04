package application.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class SupplierEntity(
    @PrimaryKey val id: String,
    val name: String,
    val contactPerson: String?,
    val email: String?,
    val phone: String?
)
