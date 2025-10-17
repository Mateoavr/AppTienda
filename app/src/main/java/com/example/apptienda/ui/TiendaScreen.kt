package com.example.apptienda.ui.theme


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.viewmodel.CarritoViewModel
import com.example.apptienda.viewmodel.ProductoViewModel
import com.example.apptienda.viewmodel.UsuarioViewModel
@Composable
fun TiendaScreen(
    productoVM: ProductoViewModel,
    carritoVM: CarritoViewModel,
    usuarioVM: UsuarioViewModel
) {
    val usuario by usuarioVM.usuario.collectAsState()

    var nombre by remember { mutableStateOf("") }
    val productos by productoVM.productos.collectAsState()
    val carrito by carritoVM.carrito.collectAsState()
    val total = carrito.sumOf { it.precioUnitario * it.cantidad }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(NegroFondo)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("🛒 Tienda Level-Up Gamer", color = VerdeNeon, fontSize = 24.sp)
        Spacer(Modifier.height(12.dp))
        Text("Bienvenido, ${usuario!!.nombre}", color = BlancoTexto, fontSize = 20.sp)
        Spacer(Modifier.height(12.dp))


        //Lista de producto
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
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

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        //Carrito
        Text("🧾 Carrito", color = BlancoTexto, fontSize = 18.sp)
        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío.", color = GrisClaroTexto)
        } else {
            carrito.forEach { item ->
                Text(
                    "• ${item.codigoProducto} x${item.cantidad} = ${item.precioUnitario * item.cantidad} CLP",
                    color = GrisClaroTexto
                )
            }

            Spacer(Modifier.height(8.dp))
            Text("Total: $total CLP", color = VerdeNeon, fontSize = 20.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { carritoVM.vaciarCarrito() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Vaciar", color = BlancoTexto)
                }

                //Pagar
                Button(
                    onClick = {

                        carritoVM.vaciarCarrito()
                        Toast.makeText(context, "Pago Exitoso!!!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                ) {
                    Text("Pagar", color = NegroFondo)
                }
            }
        }
    }

}