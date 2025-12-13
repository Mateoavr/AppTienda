package com.example.apptienda.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Producto(
    @PrimaryKey val codigo: String,
    val categoria: String,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val rating: Float? = null,
    val imagen: String? = null,
    val resenias: List<Resenia> = emptyList()
)