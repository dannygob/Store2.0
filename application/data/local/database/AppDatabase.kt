package application.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import application.data.local.dao.ProductDao
import application.data.local.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
