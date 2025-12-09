package com.example.apptienda.ui.theme

import coil.compose.AsyncImage
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.model.local.Producto
import com.example.apptienda.viewmodel.CarritoViewModel
import com.example.apptienda.viewmodel.ProductoViewModel
import com.example.apptienda.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import java.util.Locale

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


    var productoAcalificar by remember { mutableStateOf<Producto?>(null) }
    var ratingSeleccionado by remember { mutableIntStateOf(0) }
    var comentarioResena by remember { mutableStateOf("") }


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
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = GrisOscuroCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        val urlImagen = "http://10.0.2.2:8080/imagen/${producto.imagen}"
                        AsyncImage(
                            model = urlImagen,
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))


                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = producto.nombre,
                                color = BlancoTexto,
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 20.sp
                            )

                            Text(
                                text = "$${producto.precio} CLP",
                                color = VerdeNeon,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                val texto = "¡Mira este producto: ${producto.nombre}!"
                                                putExtra(Intent.EXTRA_SUBJECT, "Producto Increíble")
                                                putExtra(Intent.EXTRA_TEXT, texto)
                                            }
                                            val chooser = Intent.createChooser(intent, "Compartir")
                                            context.startActivity(chooser)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Share, contentDescription = "Share", tint = VerdeNeon, modifier = Modifier.size(20.dp))
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(
                                        onClick = {
                                            if (usuario != null) {
                                                productoAcalificar = producto
                                                ratingSeleccionado = 0
                                                comentarioResena = ""
                                            } else {
                                                Toast.makeText(context, "Inicia sesión", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Star, contentDescription = "Rate", tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                                    }
                                }

                                Button(
                                    onClick = {
                                        usuario?.id?.let { idUsuario ->
                                            scope.launch {
                                                if(carritoVM.agregarProducto(producto, idUsuario)) {
                                                    Toast.makeText(context, "Agregado", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } ?: Toast.makeText(context, "Inicia sesión", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Agregar", color = NegroFondo, fontSize = 12.sp)
                                }
                            }
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
                            Text(
                                "• ${item.nombreProducto} x${item.cantidad} = $${String.format(Locale.US, "%.0f", (item.precioUnitario * item.cantidad).toDouble())}",
                                color = GrisClaroTexto
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text("Total: $${String.format(Locale.US, "%.0f", total.toDouble())} CLP", color = VerdeNeon, fontSize = 20.sp)
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
                                val productosEnCarrito = carrito.mapNotNull { itemCarrito ->
                                    todosLosProductos.find { it.codigo == itemCarrito.productoCodigo }
                                }
                                if (productosEnCarrito.isNotEmpty()) {
                                    usuarioVM.agregarAlHistorial(productosEnCarrito)
                                    Toast.makeText(context, "Pago realizado con éxito 🎮", Toast.LENGTH_SHORT).show()
                                    usuario?.id?.let { carritoVM.vaciarCarrito(it) }
                                } else {
                                    Toast.makeText(context, "Carrito vacío o error", Toast.LENGTH_SHORT).show()
                                }
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

    if (productoAcalificar != null) {
        AlertDialog(
            onDismissRequest = { productoAcalificar = null },
            containerColor = GrisOscuroCard,
            title = {
                Text("Calificar: ${productoAcalificar!!.nombre}", color = VerdeNeon)
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Toca las estrellas para calificar:", color = GrisClaroTexto)
                    Spacer(modifier = Modifier.height(8.dp))
                    RatingBarInteractivo(rating = ratingSeleccionado) { ratingSeleccionado = it }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = comentarioResena,
                        onValueChange = { comentarioResena = it },
                        label = { Text("Escribe tu opinión") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = BlancoTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = GrisClaroTexto,
                            focusedLabelColor = VerdeNeon,
                            unfocusedLabelColor = GrisClaroTexto
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (ratingSeleccionado > 0) {
                            productoVM.agregarResenia(
                                codigo = productoAcalificar!!.codigo,
                                comentario = comentarioResena,
                                calificacion = ratingSeleccionado,
                                nombreUsuario = nombreUsuario
                            )
                            Toast.makeText(context, "¡Gracias por tu opinión!", Toast.LENGTH_SHORT).show()
                            productoAcalificar = null
                        } else {
                            Toast.makeText(context, "Selecciona al menos 1 estrella", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                ) {
                    Text("Enviar", color = NegroFondo)
                }
            },
            dismissButton = {
                TextButton(onClick = { productoAcalificar = null }) {
                    Text("Cancelar", color = GrisClaroTexto)
                }
            }
        )
    }
}

@Composable
fun RatingBarInteractivo(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Default.StarBorder,
                contentDescription = "Estrella $i",
                tint = Color(0xFFFFD700),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onRatingChanged(i) }
                    .padding(4.dp)
            )
        }
    }
}

