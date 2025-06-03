package com.store20.data.local.entities // Updated package

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val address: String?
)
