package application.data.remote.api

import application.domain.models.Product // Import the new Product model
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>

    @GET("products/{productId}") // New endpoint
    suspend fun getProductById(@Path("productId") productId: Int): Product
}
