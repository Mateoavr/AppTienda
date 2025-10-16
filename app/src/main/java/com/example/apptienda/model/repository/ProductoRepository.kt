package com.example.apptienda.model.repository


import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.local.ProductoDao
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {
    fun obtenerProductos(): Flow<List<Producto>> = productoDao.obtenerTodos()

    suspend fun insertarProductos(productos: List<Producto>) {
        productoDao.insertarTodos(productos)
    }
}