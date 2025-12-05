package com.example.apptienda.ui.theme

import coil.compose.AsyncImage
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.viewmodel.CarritoViewModel
import com.example.apptienda.viewmodel.ProductoViewModel
import com.example.apptienda.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun TiendaScreen(
    productoVM: ProductoViewModel,
    carritoVM: CarritoViewModel,
    usuarioVM: UsuarioViewModel
) {

    val usuario by usuarioVM.usuario.collectAsState()
    val todosLosProductos by productoVM.productos.collectAsState()
    val carrito by carritoVM.carrito.collectAsState()


    var searchText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // 3. Lógica de Filtrado
    val productosFiltrados = if (searchText.isBlank()) {
        todosLosProductos
    } else {
        todosLosProductos.filter {
            it.nombre.contains(searchText, ignoreCase = true) ||
                    it.categoria.contains(searchText, ignoreCase = true)
        }
    }

    val total = carrito.sumOf { it.precioUnitario * it.cantidad }

    val nombreUsuario = usuario?.nombre ?: "Invitado"


    val NegroFondo = Color(0xFF121212L)
    val VerdeNeon = Color(0xFF39FF14L)
    val BlancoTexto = Color.White
    val GrisClaroTexto = Color.LightGray
    val GrisOscuroCard = Color(0xFF1C1C1CL)
    val GrisFondoTextField = Color(0xFF1E1E1EL)


    LaunchedEffect(Unit) { productoVM.cargarProductos() }

    LaunchedEffect(usuario) {

        usuario?.id?.let { idUsuario ->
            carritoVM.cargarCarrito(idUsuario)
        } ?: carritoVM.limpiarCarritoLocal()
    }



    Column(
        modifier = Modifier
            .background(NegroFondo)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Tienda", color = VerdeNeon, fontSize = 24.sp)
        Spacer(Modifier.height(12.dp))

        Text("Bienvenido, $nombreUsuario", color = BlancoTexto, fontSize = 20.sp)
        Spacer(Modifier.height(12.dp))


        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar...") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = GrisFondoTextField,
                unfocusedContainerColor = GrisFondoTextField,
                focusedTextColor = BlancoTexto,
                unfocusedTextColor = BlancoTexto,
                cursorColor = VerdeNeon,
                focusedLabelColor = VerdeNeon,
                unfocusedLabelColor = GrisClaroTexto
            )
        )
        Spacer(Modifier.height(10.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(productosFiltrados) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisOscuroCard)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val urlImagen = "http://10.0.2.2:8080/imagen/${producto.imagen}"

                        AsyncImage(
                            model = urlImagen,
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(end = 12.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text(text = producto.nombre, color = BlancoTexto, fontSize = 16.sp)
                            Text(text = "$${producto.precio} CLP", color = VerdeNeon)
                        }


                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                val textoCompartir = "¡Mira este producto: ${producto.nombre}!"
                                putExtra(Intent.EXTRA_SUBJECT, "Producto Increíble")
                                putExtra(Intent.EXTRA_TEXT, textoCompartir)
                            }
                            val chooser = Intent.createChooser(intent, "Compartir producto")
                            context.startActivity(chooser)
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Compartir", tint = VerdeNeon)
                        }


                        Button(
                            onClick = {
                                usuario?.id?.let { idUsuario ->
                                    scope.launch {
                                        val exito = carritoVM.agregarProducto(producto, idUsuario)
                                        if (exito) {
                                            Toast.makeText(context, "${producto.nombre} agregado", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Error al agregar", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } ?: run {
                                    Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
                                }
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
        Text("Carrito", color = BlancoTexto, fontSize = 18.sp)
        Spacer(Modifier.height(4.dp))


        AnimatedVisibility(visible = carrito.isEmpty()) {
            Text("Tu carrito está vacío", color = GrisClaroTexto)
        }


        AnimatedVisibility(visible = carrito.isNotEmpty()) {
            Column {

                Column(modifier = Modifier.heightIn(max = 100.dp)) {
                    LazyColumn {
                        items(carrito) { item ->
                            Text("• ${item.nombreProducto} x${item.cantidad} = $${String.format("%.2f", (item.precioUnitario * item.cantidad).toDouble())} CLP", color = GrisClaroTexto)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))


                Text("Total: $${String.format("%.2f", total.toDouble())} CLP", color = VerdeNeon, fontSize = 20.sp)
                Spacer(Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = { usuario?.id?.let { carritoVM.vaciarCarrito(it) } },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text("Vaciar", color = BlancoTexto)
                    }


                    Button(
                        onClick = {
                            if (usuario != null) {
                                Toast.makeText(context, "Pago realizado con éxito 🎮", Toast.LENGTH_SHORT).show()
                                usuario?.id?.let { carritoVM.vaciarCarrito(it) }
                            } else {
                                Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                    ) {
                        Text("Pagar", color = NegroFondo, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}