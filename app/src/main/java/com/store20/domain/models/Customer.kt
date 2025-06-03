package com.store20.domain.models // Updated package

data class Customer(
    val id: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val address: String?
)
