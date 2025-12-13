package com.example.apptienda.model.repository

import android.util.Log
import com.example.apptienda.model.local.Resenia
import com.example.apptienda.model.remote.ApiService

class ReseniaRepository (private val api: ApiService) {


    suspend fun enviarResenia(resenia: Resenia): Boolean {
        return try {
            val response = api.crearResenia(resenia)
            if (response.isSuccessful) {
                Log.d("API", "Reseña guardada con éxito")
                true
            } else {
                Log.e("API", "Error al guardar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("API", "Error de conexión: ${e.message}")
            false
        }
    }

    suspend fun obtenerResenias(codigo: String): List<Resenia> {
        return try {
            api.obtenerResenias(codigo)
        } catch (e: Exception) {
            emptyList()
        }
    }
}