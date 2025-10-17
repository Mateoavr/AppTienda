package com.example.apptienda.ui.theme



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptienda.model.local.AppDatabase
import com.example.apptienda.model.repository.CarritoRepository
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.model.repository.UsuarioRepository
import com.example.apptienda.viewmodel.*

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = NegroFondo) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = VerdeNeon) },
                    label = { Text("Cuenta", color = BlancoTexto) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = VerdeNeon) },
                    label = { Text("Tienda", color = BlancoTexto) }
                )
            }
        },
        containerColor = NegroFondo
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> CuentaScreen()
                1 -> TiendaScreen()
            }
        }
    }
}

@Composable
fun CuentaScreen() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val usuarioDao = database.usuarioDao()
    val usuarioRepository = UsuarioRepository(usuarioDao)
    val factory = ViewModelFactory(
        ProductoRepository(database.productoDao()),
        usuarioRepository,
        CarritoRepository(database.carritoDao())
    )
    val usuarioVM: UsuarioViewModel = viewModel(factory = factory)

    val usuario by usuarioVM.usuario.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("Otro") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(NegroFondo)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("👤 Cuenta de Usuario", color = VerdeNeon, fontSize = 24.sp)
        Spacer(Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(Modifier.padding(16.dp)) {
                if (usuario == null) {
                    Text("Registro / Ingreso", color = BlancoTexto, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
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
                        label = { Text("Correo electrónico") },
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
                        value = edad,
                        onValueChange = { edad = it },
                        label = { Text("Edad") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(Modifier.height(6.dp))
                    Text("Género", color = BlancoTexto)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Masculino", "Femenino", "Otro").forEach { opcion ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .selectable(
                                        selected = (genero == opcion),
                                        onClick = { genero = opcion }
                                    )
                            ) {
                                RadioButton(
                                    selected = (genero == opcion),
                                    onClick = { genero = opcion },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = VerdeNeon,
                                        unselectedColor = Color.Gray
                                    )
                                )
                                Text(opcion, color = BlancoTexto)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña (mínimo 6 caracteres)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { usuarioVM.registrar(nombre, correo, contrasena) },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text("Registrar", color = NegroFondo)
                        }
                        Button(
                            onClick = { usuarioVM.login(correo, contrasena) },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text("Ingresar", color = NegroFondo)
                        }
                    }
                } else {
                    Text("Hola, ${usuario!!.nombre}", color = BlancoTexto, fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { usuarioVM.logout() },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                    ) {
                        Text("Cerrar Sesión", color = NegroFondo)
                    }
                }
            }
        }
    }
}

@Composable
fun TiendaScreen() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val factory = ViewModelFactory(
        ProductoRepository(database.productoDao()),
        UsuarioRepository(database.usuarioDao()),
        CarritoRepository(database.carritoDao())
    )
    val productoVM: ProductoViewModel = viewModel(factory = factory)
    val carritoVM: CarritoViewModel = viewModel(factory = factory)

    val productos by productoVM.productos.collectAsState()
    val carrito by carritoVM.carrito.collectAsState()
    val total = carrito.sumOf { it.precioUnitario * it.cantidad }

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
                            Text("Agregar", color = NegroFondo)
                        }
                    }
                }
            }
        }

        Divider(color = Color.Gray)
        Text("Carrito", color = BlancoTexto, fontSize = 18.sp)
        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío.", color = GrisClaroTexto)
        } else {
            carrito.forEach { item ->
                Text("• ${item.codigoProducto} x${item.cantidad} = ${item.precioUnitario * item.cantidad} CLP", color = GrisClaroTexto)
            }
        }

        Spacer(Modifier.height(8.dp))
        Text("Total: $total CLP", color = VerdeNeon, fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { carritoVM.vaciarCarrito() }) {
                Text("Vaciar")
            }
            Button(onClick = {}) {
                Text("Pagar")
            }
        }
    }
}