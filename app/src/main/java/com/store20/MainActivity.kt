package com.store20

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import application.presentation.screens.dashboard.DashboardScreen
import application.presentation.screens.inventory.AddProductScreen
import application.presentation.screens.inventory.InventoryScreen
import dagger.hilt.android.AndroidEntryPoint
@@ -18,6 +19,7 @@ import androidx.navigation.NavType
import androidx.navigation.navArgument

object AppDestinations {
    const val DASHBOARD = "dashboard"
    const val INVENTORY_LIST = "inventoryList"
    const val ADD_PRODUCT_ROUTE = "addProduct" // Base route
    const val PRODUCT_ID_ARG = "productId"
@@ -37,7 +39,10 @@ class MainActivity : ComponentActivity() {
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = AppDestinations.INVENTORY_LIST) {
                NavHost(navController = navController, startDestination = AppDestinations.DASHBOARD) {
                    composable(AppDestinations.DASHBOARD) {
                        DashboardScreen(navController = navController)
                    }
                    composable(AppDestinations.INVENTORY_LIST) {
                        InventoryScreen(navController = navController)
                    }
 28 changes: 28 additions & 0 deletions28  
application/data/local/dao/SupplierDao.kt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,28 @@
package application.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import application.data.local.entities.SupplierEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplier(supplier: SupplierEntity)

    @Query("SELECT * FROM suppliers WHERE id = :supplierId")
    suspend fun getSupplierById(supplierId: String): SupplierEntity?

    @Query("SELECT * FROM suppliers ORDER BY name ASC")
    fun getAllSuppliers(): Flow<List<SupplierEntity>>

    @Update
    suspend fun updateSupplier(supplier: SupplierEntity)

    @Delete
    suspend fun deleteSupplier(supplier: SupplierEntity)
}
  5 changes: 4 additions & 1 deletion5  
application/data/local/database/AppDatabase.kt
Original file line number	Diff line number	Diff line change
@@ -3,9 +3,12 @@ package application.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import application.data.local.dao.ProductDao
import application.data.local.dao.SupplierDao
import application.data.local.entities.ProductEntity
import application.data.local.entities.SupplierEntity

@Database(entities = [ProductEntity::class], version = 1)
@Database(entities = [ProductEntity::class, SupplierEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun supplierDao(): SupplierDao
}
