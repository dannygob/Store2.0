package com.store20.data.repository // Updated package

import com.store20.data.local.dao.ProductDao // Updated import
import com.store20.data.local.entities.ProductEntity // Updated import
import com.store20.domain.models.Product // Updated import
import com.store20.domain.repository.ProductRepository // Updated import
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val firestore: FirebaseFirestore
) : ProductRepository {

    override suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product.toEntity())
    }

    override suspend fun getProductById(productId: String): Product? {
        return productDao.getProductById(productId)?.toDomain()
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product.toEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product.toEntity())
    }

    override suspend fun syncProductToFirebase(product: Product): Result<Unit> {
        return try {
            // Placeholder: Log action. Actual implementation would involve:
            // firestore.collection("products").document(product.ID).set(product)
            // .await() // using kotlinx-coroutines-play-services for await()
            println("FirebaseSync: Attempting to sync product ${product.ID} to Firebase.")
            println("FirebaseSync: Product data: $product")
            // Simulate success for now as we can't test actual Firebase calls
            Result.success(Unit)
        } catch (e: Exception) {
            println("FirebaseSync: Error syncing product ${product.ID} to Firebase: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteProductFromFirebase(productId: String): Result<Unit> {
        return try {
            // Placeholder: Log action. Actual implementation would involve:
            // firestore.collection("products").document(productId).delete()
            // .await()
            println("FirebaseSync: Attempting to delete product $productId from Firebase.")
            // Simulate success
            Result.success(Unit)
        } catch (e: Exception) {
            println("FirebaseSync: Error deleting product $productId from Firebase: ${e.message}")
            Result.failure(e)
        }
    }

    // Helper functions for mapping
    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            ID = ID,
            Name = Name,
            Barcode = Barcode,
            PurchasePrice = PurchasePrice,
            SalePrice = SalePrice,
            Category = Category,
            Stock = Stock,
            SupplierID = SupplierID
        )
    }

    private fun ProductEntity.toDomain(): Product {
        return Product(
            ID = ID,
            Name = Name,
            Barcode = Barcode,
            PurchasePrice = PurchasePrice,
            SalePrice = SalePrice,
            Category = Category,
            Stock = Stock,
            SupplierID = SupplierID
        )
    }
}
