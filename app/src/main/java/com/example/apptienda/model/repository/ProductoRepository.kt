package com.example.apptienda.model.repository


import android.util.Log
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.remote.ApiService
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val apiService: ApiService) {


    suspend fun obtenerProductos(): List<Producto> {
        return try {
            val response = apiService.getProductos()

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error al obtener productos: ${response.code()}")
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error al obtener productos: ${e.message}")
            emptyList()
        }
    }





data class Login(
    val correo: String,
    val contrasena: String
)


data class Registro(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val fechaNacimiento: String
)
}