package com.example.apptienda.model.controller

import androidx.room.PrimaryKey


data class CarritoAdd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoCodigo: String,
    var cantidad: Int,
    val precioUnitario: Int,
    val usuarioId:Long,
    val nombreProducto: String
)