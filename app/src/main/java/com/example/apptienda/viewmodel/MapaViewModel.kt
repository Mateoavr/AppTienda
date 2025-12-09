package com.example.apptienda.viewmodel


import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.apptienda.model.local.EventoGame
import com.google.android.gms.maps.model.LatLng

class MapaViewModel : ViewModel() {

    var eventos = mutableStateListOf<EventoGame>()
        private set

    init {
        cargarEventos()
    }

    private fun cargarEventos() {

        eventos.add(EventoGame(1, "Torneo LoL Santiago", "Gran final nacional", LatLng(-33.4489, -70.6693), 100))
        eventos.add(EventoGame(2, "Expo Gamer Valpo", "Feria de videojuegos", LatLng(-33.0472, -71.6127), 50))
        eventos.add(EventoGame(3, "Lanzamiento Concepción", "Prueba de consolas", LatLng(-36.8201, -73.0444), 75))
    }

    fun reclamarPuntos(evento: EventoGame, miUbicacion: LatLng?): String {
        if (miUbicacion == null) return "Ubicación no disponible"
        if (evento.reclamado) return "Ya reclamaste estos puntos"

        val distancia = calcularDistancia(evento.ubicacion, miUbicacion)

        if (distancia < 50) {
            val index = eventos.indexOf(evento)
            eventos[index] = evento.copy(reclamado = true)
            return "¡Ganaste ${evento.puntosLevelU} Puntos LevelU!"
        } else {
            return "Estás muy lejos del evento (${distancia.toInt()} metros)"
        }
    }
    private fun calcularDistancia(p1: LatLng, p2: LatLng): Float {
        val loc1 = Location("").apply { latitude = p1.latitude; longitude = p1.longitude }
        val loc2 = Location("").apply { latitude = p2.latitude; longitude = p2.longitude }
        return loc1.distanceTo(loc2)
    }
}
