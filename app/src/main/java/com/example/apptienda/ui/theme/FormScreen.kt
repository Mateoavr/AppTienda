package com.example.apptienda.ui.theme



import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptienda.model.local.AppDatabase
import com.example.apptienda.model.repository.CarritoRepository
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.model.repository.UsuarioRepository
import com.example.apptienda.viewmodel.CarritoViewModel
import com.example.apptienda.viewmodel.ProductoViewModel
import com.example.apptienda.viewmodel.UsuarioViewModel
import com.example.apptienda.viewmodel.ViewModelFactory

@Composable
fun FormScreen() {

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val factory = ViewModelFactory(
        ProductoRepository(database.productoDao()),
        UsuarioRepository(database.usuarioDao()),
        CarritoRepository(database.carritoDao())
    )
    val productoVM: ProductoViewModel = viewModel(factory = factory)
    val usuarioVM: UsuarioViewModel = viewModel(factory = factory)
    val carritoVM: CarritoViewModel = viewModel(factory = factory)

    val productos by productoVM.productos.collectAsState()
    val usuario by usuarioVM.usuario.collectAsState()
    val carrito by carritoVM.carrito.collectAsState()
    val total = carrito.sumOf { it.precioUnitario * it.cantidad }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

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
        Spacer(Modifier.height(10.dp))

        if (usuario == null) {
            Text("Iniciar sesión o registrarse", color = BlancoTexto)


            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre (solo para registro)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlancoTexto,
                    unfocusedTextColor = GrisClaroTexto,
                    cursorColor = VerdeNeon,
                    focusedBorderColor = VerdeNeon,
                    unfocusedBorderColor = Color.Gray
                )
            )


            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlancoTexto,
                    unfocusedTextColor = GrisClaroTexto,
                    cursorColor = VerdeNeon,
                    focusedBorderColor = VerdeNeon,
                    unfocusedBorderColor = Color.Gray
                )
            )


            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlancoTexto,
                    unfocusedTextColor = GrisClaroTexto,
                    cursorColor = VerdeNeon,
                    focusedBorderColor = VerdeNeon,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { usuarioVM.registrar(nombre, correo, contrasena) }) {
                    Text("Registrar")
                }
                Button(onClick = { usuarioVM.login(correo, contrasena) }) {
                    Text("Ingresar")
                }
            }
        } else {
            Text("Hola, ${usuario!!.nombre}", color = BlancoTexto, fontSize = 20.sp)
            Button(onClick = { usuarioVM.logout() }) { Text("Cerrar Sesión") }
        }

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))


        Text("Catálogo", color = BlancoTexto, fontSize = 18.sp)
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
                            onClick = { carritoVM.agregarProducto(producto) },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text(text = "Agregar", color = NegroFondo)
                        }
                    }
                }
            }
        }

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        Text("Carrito", color = BlancoTexto, fontSize = 18.sp)
        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío.", color = GrisClaroTexto)
        } else {
            carrito.forEach { item ->
                Text(
                    "• ${item.codigoProducto} x${item.cantidad} = ${item.precioUnitario * item.cantidad} CLP",
                    color = GrisClaroTexto
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Text("Total: $total CLP", color = VerdeNeon, fontSize = 20.sp)
        Button(
            onClick = { carritoVM.vaciarCarrito() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vaciar Carrito")
        }
    }
}
