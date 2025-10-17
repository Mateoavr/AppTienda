package com.example.apptienda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptienda.model.local.AppDatabase
import com.example.apptienda.model.repository.CarritoRepository
import com.example.apptienda.model.repository.ProductoRepository
import com.example.apptienda.model.repository.UsuarioRepository


import com.example.apptienda.ui.theme.*
import com.example.apptienda.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTiendaTheme {

                val context = LocalContext.current
                val database = remember { AppDatabase.getDatabase(context) }
                val factory = remember {
                    val productoRepo = ProductoRepository(database.productoDao())
                    val usuarioRepo = UsuarioRepository(database.usuarioDao())
                    val carritoRepo = CarritoRepository(database.carritoDao())
                    ViewModelFactory(productoRepo, usuarioRepo, carritoRepo)
                }


                val usuarioVM: UsuarioViewModel = viewModel(factory = factory)
                val productoVM: ProductoViewModel = viewModel(factory = factory)
                val carritoVM: CarritoViewModel = viewModel(factory = factory)


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
                            0 -> CuentaScreen(usuarioVM)
                            1 -> TiendaScreen(productoVM, carritoVM,usuarioVM)
                        }
                    }
                }
            }
        }
    }
}
