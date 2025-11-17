package com.example.apptienda.model.repository

import android.util.Log
import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.remote.ApiService

class UsuarioRepository(
    private val apiService: ApiService
) {


    suspend fun login(correo: String, contrasena: String): Usuario? {
        return try {
            val request = ProductoRepository.Login(correo, contrasena)
            val response = apiService.login(request)


            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UsuarioRepository", "Error en login: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Excepción en login: ${e.message}")
            null
        }
    }


    suspend fun registrar(request: ProductoRepository.Registro): Usuario? {
        return try {
            val response = apiService.registrar(request)


            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("UsuarioRepository", "Error en registro: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Excepción en registro: ${e.message}")
            null
        }
    }


    suspend fun obtenerUsuarios(): List<Usuario> {
        return try {
            val response = apiService.getUsuarios()


            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("UsuariosRepository", "Fallo al obtener usuarios: Código ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("UsuariosRepository", "Error de red al obtener usuarios: ${e.message}")
            emptyList()
        }
    }
}