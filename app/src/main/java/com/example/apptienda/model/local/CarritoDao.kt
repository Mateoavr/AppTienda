package com.example.apptienda.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Insert
    suspend fun insertar(item: Carrito)

    @Update
    suspend fun actualizar(item: Carrito)

    @Query("DELETE FROM carrito")
    suspend fun vaciar()

    @Query("SELECT * FROM carrito")
    fun obtenerTodos(): Flow<List<Carrito>>

    @Query("SELECT * FROM carrito WHERE codigoProducto = :codigoProducto LIMIT 1")
    suspend fun obtenerItem(codigoProducto: String): Carrito?
}