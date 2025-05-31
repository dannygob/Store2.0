package application.data.remote.api

import application.domain.models.Product // Import the new Product model
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{categoryName}") // New endpoint
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>
}
