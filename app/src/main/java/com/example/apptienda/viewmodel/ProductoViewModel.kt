package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repo: ProductoRepository
) : ViewModel() {


    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()


    fun cargarProductos() {
        viewModelScope.launch {
            val productosObtenidos = repo.obtenerProductos()
            _productos.value = productosObtenidos
        }
    }
}