package com.example.apptienda.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.local.Resenia
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.model.repository.ReseniaRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repo: ProductoRepository,
    private val reseniaRepo: ReseniaRepository
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = repo.obtenerProductos()
        }
    }

    fun agregarResenia(
        codigo: String,
        comentario: String,
        calificacion: Int,
        nombreUsuario: String
    ) {
        viewModelScope.launch {
            try {
                val nuevaResenia = Resenia(
                    codigo = codigo,
                    usuario = nombreUsuario,
                    comentario = comentario,
                    calificacion = calificacion
                )

                val exito = reseniaRepo.enviarResenia(nuevaResenia)

                if (exito) {
                    Log.d("ProductoViewModel", "Reseña enviada correctamente")
                } else {
                    Log.e("ProductoViewModel", "Backend rechazó la reseña")
                }

            } catch (e: Exception) {
                Log.e("ProductoViewModel", "Error al enviar reseña", e)
            }
        }
    }
}