package com.example.apptienda.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Usuario


import com.example.apptienda.model.repository.ProductoRepository

import com.example.apptienda.model.repository.UsuarioRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

import java.util.Locale

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {


    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()

    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri = _fotoUri.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()


    init {
        obtenerUsuarios()
    }

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            val usuarioLogueado = repository.login(correo, contrasena)

            _usuario.value = usuarioLogueado

            _error.value = if (usuarioLogueado == null) {
                "Correo o contraseña incorrectos"
            } else {
                null
            }
        }
    }


    fun registrar(
        nombre: String,
        correo: String,
        contrasena: String,
        fechaNacimientoStr: String
    ) {
        viewModelScope.launch {
            val request =
                ProductoRepository.Registro(nombre, correo, contrasena, fechaNacimientoStr)
            val usuarioRegistrado = repository.registrar(request)

            _usuario.value = usuarioRegistrado

            _error.value = if (usuarioRegistrado == null) {
                "Error en el registro. Inténtalo de nuevo."
            } else {
                null
            }
        }
    }


    fun logout() {
        _usuario.value = null
    }


    fun limpiarError() {
        _error.value = null
    }


    fun calculaEdad(fechaNacimientoStr: String): Int {
        Log.d("FECHA_DEBUG", "Valor recibido: $fechaNacimientoStr")

        return try {
            val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val birthDate = formato.parse(fechaNacimientoStr) ?: return 0

            val today = Calendar.getInstance()
            val birth = Calendar.getInstance().apply { time = birthDate }

            var edad = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)

            if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }

            edad

        } catch (e: Exception) {
            Log.e("UsuarioViewModel", "Error al parsear fecha: $fechaNacimientoStr", e)
            0
        }
    }


    fun obtenerUsuarios() {
        viewModelScope.launch {
            try {
                val listaUsuarios = repository.obtenerUsuarios()

                if (listaUsuarios.isNotEmpty()) {
                    _usuarios.value = listaUsuarios
                } else {
                    _error.value = "No se pudieron cargar los usuarios o la lista está vacía."
                }

            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error al obtener usuarios: ${e.message}")
                _error.value = "Error de conexión al intentar obtener la lista de usuarios."
            }
        }
    }


    private fun getFilePathFromUri(uri: Uri, context: Context): String? {
        return uri.path
    }
}