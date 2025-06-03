package application.domain.models

data class Customer(
    val id: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val address: String?
)
