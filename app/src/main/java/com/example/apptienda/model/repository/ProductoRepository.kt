package com.example.apptienda.model.repository


import android.util.Log
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.local.Resenia
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

    suspend fun enviarResenia(resenia: Resenia) {
        try {
            val response = apiService.enviarResenia(resenia)
            if (response.isSuccessful) {
                Log.d("ReseniaRepo", "Se guardó correctamente: ${response.code()}")
            } else {
                Log.e("ReseniaRepo", "Error del servidor: ${response.message()}")
            }
        } catch (e: Exception) {
            // Error de conexión (ej: sin internet)
            Log.e("ReseniaRepo", "Falló la conexión: ${e.message}")
            e.printStackTrace()
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