package application.di

import android.content.Context
import androidx.room.Room
import application.data.local.dao.ProductDao
import application.data.local.database.AppDatabase
import application.data.repository.ProductRepositoryImpl
import application.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit // Import Retrofit
import retrofit2.converter.gson.GsonConverterFactory // Import Gson converter
import application.data.remote.api.FakeStoreApiService // Import new service
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        firestore: FirebaseFirestore // Add this parameter
    ): ProductRepository {
        return ProductRepositoryImpl(productDao, firestore) // Pass it here
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): com.google.firebase.auth.FirebaseAuth {
        return com.google.firebase.auth.FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFakeStoreApiService(retrofit: Retrofit): FakeStoreApiService {
        return retrofit.create(FakeStoreApiService::class.java)
    }

    // TODO: Add other providers here (e.g., Firestore, Repositories)
}
