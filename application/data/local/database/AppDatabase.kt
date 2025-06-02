package application.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import application.data.local.dao.ProductDao
import application.data.local.dao.SupplierDao
import application.data.local.entities.ProductEntity
import application.data.local.entities.SupplierEntity

@Database(entities = [ProductEntity::class, SupplierEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun supplierDao(): SupplierDao
}
