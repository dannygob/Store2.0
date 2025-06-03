package com.store20.data.local.database // Updated package

import androidx.room.Database
import androidx.room.RoomDatabase
import com.store20.data.local.dao.CustomerDao // Updated import
import com.store20.data.local.dao.OrderDao // Updated import
import com.store20.data.local.dao.OrderDetailDao // Updated import
import com.store20.data.local.dao.ProductDao // Updated import
import com.store20.data.local.dao.SupplierDao // Updated import
import com.store20.data.local.entities.CustomerEntity // Updated import
import com.store20.data.local.entities.OrderEntity // Updated import
import com.store20.data.local.entities.OrderDetailEntity // Updated import
import com.store20.data.local.entities.ProductEntity // Updated import
import com.store20.data.local.entities.SupplierEntity // Updated import

@Database(
    entities = [
        ProductEntity::class,
        SupplierEntity::class,
        CustomerEntity::class,
        OrderEntity::class,
        OrderDetailEntity::class
    ],
    version = 3
    // If using AutoMigrations, you'd add:
    // autoMigrations = [AutoMigration(from = 2, to = 3)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun supplierDao(): SupplierDao
    abstract fun customerDao(): CustomerDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao

    // In a real app, define migrations or use fallbackToDestructiveMigration carefully.
    // For this project, destructive migration is acceptable for schema changes.
    // Consider adding fallbackToDestructiveMigration() in the actual database builder
    // if not using explicit migrations or auto-migrations.
    // For now, just incrementing version. Room will throw an error if migrations are not handled.
    // The instruction mentions "Ensure fallbackToDestructiveMigration() or proper migrations are in place."
    // This implies it's handled at the DB builder level, so only schema definition here.
}
