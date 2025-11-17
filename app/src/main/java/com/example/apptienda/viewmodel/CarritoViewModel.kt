package com.example.apptienda.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.controller.CarritoAdd


import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow


import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class CarritoViewModel(
    private val repository: CarritoRepository
) : ViewModel() {


    private val _carrito = MutableStateFlow<List<CarritoAdd>>(emptyList())
    val carrito = _carrito.asStateFlow()

    fun cargarCarrito(usuarioId: Long) {
        viewModelScope.launch {
            try {
                val listaCarrito = repository.getCarrito(usuarioId)
                _carrito.value = listaCarrito
            } catch (e: Exception) {
                Log.e("CarritoViewModel", "Error al cargar carrito: ${e.message}")
            }
        }
    }


    suspend fun agregarProducto(producto: Producto, usuarioId: Long): Boolean {
        val request = CarritoAdd(
            productoCodigo = producto.codigo,
            cantidad = 1,
            usuarioId = usuarioId,
            nombreProducto = producto.nombre,
            precioUnitario = producto.precio
        )

        val exito = repository.agregarProducto(request)

        if (exito) {
            cargarCarrito(usuarioId)
        }

        return exito
    }


    fun vaciarCarrito(usuarioId: Long) {
        viewModelScope.launch {
            repository.vaciarCarrito(usuarioId)
            _carrito.value = emptyList()
        }
    }


    fun limpiarCarritoLocal() {
        _carrito.value = emptyList()
    }
}