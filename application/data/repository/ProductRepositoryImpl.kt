package application.data.repository

import application.data.local.dao.ProductDao
import application.data.local.entities.ProductEntity
import application.domain.models.Product
import application.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {

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
