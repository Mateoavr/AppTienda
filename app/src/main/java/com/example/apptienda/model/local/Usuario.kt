package com.example.apptienda.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class Carrito(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val codigoProducto: String,
    var cantidad: Int,
    val precioUnitario: Int
)