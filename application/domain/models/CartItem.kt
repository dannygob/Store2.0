package application.domain.models

data class CartItem(
    val id: String, // Could be product ID or a unique identifier for the product
    val name: String,
    var quantity: Int,
    val price: Double = 0.0 // Placeholder for price
) {
    // Helper to calculate total price for this cart item
    fun getTotalPrice(): Double = quantity * price
}
