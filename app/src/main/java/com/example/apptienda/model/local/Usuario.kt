package com.example.apptienda.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "usuarios")
data class Usuario(
    val id: Long = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val fechaNacimiento: String,
    val esDuoc: Boolean = false
)