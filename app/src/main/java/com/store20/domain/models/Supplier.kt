package com.store20.domain.models

data class Supplier(
    val id: String,
    val name: String,
    val contactPerson: String?,
    val email: String?,
    val phone: String?
)
