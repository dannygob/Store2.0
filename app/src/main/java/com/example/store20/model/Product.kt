package com.example.store20.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String // Can be a URL or a local drawable resource name
)
