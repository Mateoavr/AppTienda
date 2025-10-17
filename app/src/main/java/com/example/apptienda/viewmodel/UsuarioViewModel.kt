package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.repository.UsuarioRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario = _usuario.asStateFlow()

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            val user = repository.login(correo, contrasena)
            _usuario.value = user
            // Aquí podrías agregar lógica para manejar un login fallido (user == null)
        }
    }

    fun registrar(nombre: String, correo: String, contrasena: String) {
        viewModelScope.launch {
            // Lógica de ejemplo para registrar un nuevo usuario mayor de 18
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -19)
            val fechaNacimientoEjemplo: Date = calendar.time

            val esDuoc = correo.endsWith("@duoc.cl")

            val nuevoUsuario = Usuario(
                nombre = nombre,
                correo = correo,
                contrasena = contrasena,
                fechaNacimiento = fechaNacimientoEjemplo,
                esDuoc = esDuoc
            )
            repository.registrar(nuevoUsuario)
            // Opcional: Iniciar sesión automáticamente después del registro
            _usuario.value = nuevoUsuario
        }
    }

    fun logout() {
        _usuario.value = null
    }
}