package application.domain.repository

import application.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun getProductById(productId: String): Product?
    fun getAllProducts(): Flow<List<Product>>
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)

    suspend fun syncProductToFirebase(product: Product): Result<Unit>
    suspend fun deleteProductFromFirebase(productId: String): Result<Unit>
}
