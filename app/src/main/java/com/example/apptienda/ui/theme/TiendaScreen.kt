package com.example.apptienda.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.viewmodel.CarritoViewModel
import com.example.apptienda.viewmodel.ProductoViewModel

@Composable
fun TiendaScreen(productoVM: ProductoViewModel, carritoVM: CarritoViewModel) {
    val productos by productoVM.productos.collectAsState()
    val carrito by carritoVM.carrito.collectAsState()
    val total = carrito.sumOf { it.precioUnitario * it.cantidad }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productoVM.popularBaseDeDatos()
    }

    Column(
        modifier = Modifier
            .background(NegroFondo)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🛒 Catálogo de Productos", color = VerdeNeon, fontSize = 22.sp)
        Spacer(Modifier.height(10.dp))

        //Catálogo de Productos
        LazyColumn(Modifier.weight(1f)) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = producto.nombre, color = BlancoTexto, fontSize = 16.sp)
                            Text(text = "${producto.precio} CLP", color = VerdeNeon)
                        }
                        Button(
                            onClick = {
                                carritoVM.agregarProducto(producto)

                                Toast.makeText(context, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text("Agregar", color = NegroFondo)
                        }
                    }
                }
            }
        }

        Divider(color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))

        //Carrito
        Text("Carrito", color = BlancoTexto, fontSize = 18.sp)
        Spacer(Modifier.height(4.dp))
        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío.", color = GrisClaroTexto)
        } else {
            LazyColumn(modifier = Modifier.heightIn(max = 100.dp)) {
                items(carrito) { item ->
                    Text("• ${item.codigoProducto} x${item.cantidad} = ${item.precioUnitario * item.cantidad} CLP", color = GrisClaroTexto)
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        Text("Total: $total CLP", color = VerdeNeon, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Añade espacio entre botones
        ) {
            Button(
                onClick = { carritoVM.vaciarCarrito() },
                modifier = Modifier.weight(1f), // <-- CORRECCIÓN DE LAYOUT
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Vaciar", color = BlancoTexto)
            }
            Button(
                onClick = {
                    if (carrito.isNotEmpty()) {
                        Toast.makeText(context, "Pago realizado con éxito 🎮", Toast.LENGTH_SHORT).show()
                        carritoVM.vaciarCarrito()
                    } else {
                        Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.weight(1f), // <-- CORRECCIÓN DE LAYOUT
                colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
            ) {
                Text("Pagar", color = NegroFondo, fontSize = 16.sp)
            }
        }
    }
}