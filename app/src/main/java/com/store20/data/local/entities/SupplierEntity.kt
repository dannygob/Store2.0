package com.store20.data.local.entities // Updated package

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
