package com.example.apptienda.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey val codigo: String,
    val categoria: String,
    val nombre: String,
    val precio: Int,
    val descripcion: String,
    val rating: Float? = null
)