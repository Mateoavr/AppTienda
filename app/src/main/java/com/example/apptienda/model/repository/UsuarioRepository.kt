package com.example.apptienda.model.repository

import com.example.apptienda.model.local.Usuario
import com.example.apptienda.model.local.UsuarioDao


class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrar(usuario: Usuario) {
        usuarioDao.registrar(usuario)
    }

    suspend fun login(correo: String, contrasena: String): Usuario? {
        return usuarioDao.login(correo, contrasena)
    }
}