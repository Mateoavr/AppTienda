package com.example.apptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptienda.model.local.Producto
import com.example.apptienda.model.repository.ProductoRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(private val repo: ProductoRepository) : ViewModel() {

    val productos: StateFlow<List<Producto>> = repo.obtenerProductos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun popularBaseDeDatos() = viewModelScope.launch {
        val productosDemo = listOf(
            Producto("JM001", "Juegos de Mesa", "Catan", 29990, "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan.", 4.8f),
        Producto("JM002", "Juegos de Mesa", "Carcassonne", 24990, "Un juego de colocación de fichas donde los jugadores construyen el paisaje medieval de Carcassonne.", 4.7f),
        Producto("AC001", "Accesorios", "Controlador Inalámbrico Xbox Series X", 59990, "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada.", 4.9f),
        Producto("AC002", "Accesorios", "Auriculares Gamer HyperX Cloud II", 79990, "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma.", 4.6f),
        Producto("CO001", "Consolas", "PlayStation 5", 549990, "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos.", 4.9f),
        Producto("SG001", "Sillas Gamers", "Silla Gamer Secretlab Titan", 349990, "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable.", 4.7f),
        Producto("MS001", "Mouse", "Mouse Gamer Logitech G502 HERO", 49990, "Con sensor de alta precisión y botones personalizables, ideal para gamers que buscan control.", 4.8f)
        )
        repo.insertarProductos(productosDemo)
    }
}