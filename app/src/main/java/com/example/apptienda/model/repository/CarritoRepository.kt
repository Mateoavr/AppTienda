package com.example.apptienda.model.repository


import android.util.Log
import com.example.apptienda.model.controller.CarritoAdd
import retrofit2.Response
import com.example.apptienda.model.remote.ApiService


class CarritoRepository(
    private val apiService: ApiService
) {


    suspend fun getCarrito(usuarioId: Long): List<CarritoAdd> {
        return try {
            val response = apiService.getCarrito(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun agregarProducto(request: CarritoAdd): Boolean {
        return try {
            val response = apiService.agregarAlCarrito(request)


            if (!response.isSuccessful) {
                Log.e("CarritoRepository", "Error al agregar item: ${response.code()}")
            }


            response.isSuccessful
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción al agregar item: ${e.message}")
            false
        }
    }


    suspend fun vaciarCarrito(usuarioId: Long) {
        try {
            val response = apiService.vaciarCarrito(usuarioId)


            if (!response.isSuccessful) {
                Log.e("CarritoRepository", "Error al vaciar carrito: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción al vaciar carrito: ${e.message}")
        }
    }
}