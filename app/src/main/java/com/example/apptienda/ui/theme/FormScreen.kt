package com.example.apptienda.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptienda.model.local.AppDatabase
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.viewmodel.ProductoViewModel
import com.example.apptienda.viewmodel.ViewModelFactory


@Composable
fun FormScreen(
) {
    // --- INICIO DE LA CONFIGURACIÓN DE DEPENDENCIAS ---
    val context = LocalContext.current
    // 1. Obtenemos la instancia de la base de datos
    val database = AppDatabase.getDatabase(context)
    // 2. Obtenemos el DAO del producto
    val productoDao = database.productoDao()
    // 3. Creamos el Repositorio con el DAO
    val repository = ProductoRepository(productoDao)
    // 4. Creamos la Fábrica con el Repositorio
    val factory = ViewModelFactory(repository)
    // 5. Creamos el ViewModel usando la Fábrica
    val productoVM: ProductoViewModel = viewModel(factory = factory)
    // --- FIN DE LA CONFIGURACIÓN ---

    // El resto de tu código sigue igual...
    val productos by productoVM.productos.collectAsState()

    LaunchedEffect(Unit) {
        productoVM.popularBaseDeDatos()
    }

    Column(
        modifier = Modifier
            .background(NegroFondo)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("🕹️ Level-Up Gamer", color = VerdeNeon, fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        Text("Catálogo de Productos", color = BlancoTexto, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(producto.nombre, color = BlancoTexto, fontSize = 18.sp)
                        Text(producto.categoria, color = AzulElectrico, fontSize = 12.sp)
                        Text(producto.descripcion, color = GrisClaroTexto, fontSize = 14.sp, maxLines = 2)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${producto.precio} CLP", color = VerdeNeon, fontSize = 16.sp)
                            Button(
                                onClick = { /* TODO: Agregar al carrito */ },
                                colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                            ) {
                                Text("Agregar", color = NegroFondo)
                            }
                        }
                    }
                }
            }
        }
    }
}