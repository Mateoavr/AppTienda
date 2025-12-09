package com.example.apptienda.model.local

import com.google.android.gms.maps.model.LatLng

data class EventoGame(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val ubicacion: LatLng,
    val puntosLevelU: Int,
    var reclamado: Boolean = false
)