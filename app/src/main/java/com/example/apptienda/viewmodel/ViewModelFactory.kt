package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptienda.model.repository.CarritoRepository
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.model.repository.ReseniaRepository
import com.example.apptienda.model.repository.UsuarioRepository



class ViewModelFactory(
    private val productoRepository: ProductoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val carritoRepository: CarritoRepository,
    private val reseniaRepository: ReseniaRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductoViewModel::class.java) -> {
                ProductoViewModel(
                    productoRepository,
                    reseniaRepository
                ) as T
            }

            modelClass.isAssignableFrom(UsuarioViewModel::class.java) -> {
                UsuarioViewModel(usuarioRepository) as T
            }

            modelClass.isAssignableFrom(CarritoViewModel::class.java) -> {
                CarritoViewModel(carritoRepository) as T
            }



            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}