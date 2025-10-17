package com.example.apptienda.model.repository


import com.example.apptienda.model.local.Carrito
import com.example.apptienda.model.local.CarritoDao
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val carritoDao: CarritoDao) {

    fun obtenerCarrito(): Flow<List<Carrito>> = carritoDao.obtenerTodos()

    suspend fun agregarProducto(producto: Carrito) {
        val itemExistente = carritoDao.obtenerItem(producto.codigoProducto)
        if (itemExistente != null) {
            // Si el producto ya está en el carrito, aumenta la cantidad
            itemExistente.cantidad++
            carritoDao.actualizar(itemExistente)
        } else {
            // Si es un producto nuevo, lo inserta
            carritoDao.insertar(producto)
        }
    }

    suspend fun vaciarCarrito() {
        carritoDao.vaciar()
    }
}