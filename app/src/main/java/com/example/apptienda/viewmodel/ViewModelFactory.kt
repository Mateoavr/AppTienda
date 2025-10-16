package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptienda.model.repository.ProductoRepository

class ViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        // TODO: Agrega aquí la creación de tus otros ViewModels (CarritoViewModel, UsuarioViewModel)

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}