package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Carrito
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.repository.CarritoRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    val carrito: StateFlow<List<Carrito>> = repository.obtenerCarrito()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            val nuevoItem = Carrito(
                codigoProducto = producto.codigo,
                cantidad = 1,
                precioUnitario = producto.precio
            )
            repository.agregarProducto(nuevoItem)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.vaciarCarrito()
        }
    }
}