package com.store20.data.local.dao // Updated package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.store20.data.local.entities.SupplierEntity // Updated import
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
