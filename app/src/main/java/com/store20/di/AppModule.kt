package com.store20.di // Updated package

import android.content.Context
import androidx.room.Room
import com.store20.data.local.dao.ProductDao // Updated import
import com.store20.data.local.database.AppDatabase // Updated import
import com.store20.data.repository.ProductRepositoryImpl // Updated import
import com.store20.domain.repository.ProductRepository // Updated import
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        )
        // In a real app, add migrations or fallbackToDestructiveMigration here.
        // .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    // TODO: Add providers for SupplierDao, CustomerDao, OrderDao, OrderDetailDao

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        firestore: FirebaseFirestore
    ): ProductRepository {
        return ProductRepositoryImpl(productDao, firestore)
    }

    // TODO: Add providers for other repositories (Supplier, Customer, Order)
    // if their repository interfaces and implementations are created.
}
