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

        }
    }

    fun registrar(nombre: String, correo: String, contrasena: String) {
        viewModelScope.launch {

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

            _usuario.value = nuevoUsuario
        }
    }

    fun logout() {
        _usuario.value = null
    }
}