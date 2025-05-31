package application.data.remote.api

import retrofit2.http.GET

interface FakeStoreApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<String>

    // We can add other endpoints here later, e.g., getProducts, getProductDetails
}
