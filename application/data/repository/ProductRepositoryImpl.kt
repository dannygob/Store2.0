package application.data.repository

import application.data.local.dao.ProductDao
import application.data.local.entities.ProductEntity
import application.domain.models.Product
import application.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await // For Firebase tasks with coroutines

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
            firestore.collection("products").document(product.ID).set(product).await()
            println("FirebaseSync: Product ${product.ID} successfully synced to Firebase.")
            Result.success(Unit)
        } catch (e: Exception) {
            println("FirebaseSync: Error syncing product ${product.ID} to Firebase: ${e.message}")
            e.printStackTrace() // Print stack trace for more detailed error
            Result.failure(e)
        }
    }

    override suspend fun deleteProductFromFirebase(productId: String): Result<Unit> {
        return try {
            firestore.collection("products").document(productId).delete().await()
            println("FirebaseSync: Product $productId successfully deleted from Firebase.")
            Result.success(Unit)
        } catch (e: Exception) {
            println("FirebaseSync: Error deleting product $productId from Firebase: ${e.message}")
            e.printStackTrace() // Print stack trace
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
