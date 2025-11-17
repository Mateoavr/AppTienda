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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apptienda.model.remote.Retro
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


                val apiService = remember { Retro.api }
                val productoRepo = remember { ProductoRepository(apiService) }
                val usuarioRepo = remember { UsuarioRepository(apiService) }
                val carritoRepo = remember { CarritoRepository(apiService) }

                val factory = remember {
                    ViewModelFactory(
                        productoRepository = productoRepo,
                        usuarioRepository = usuarioRepo,
                        carritoRepository = carritoRepo
                    )
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
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Cuenta",
                                        tint = VerdeNeon
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.navegacion_cuenta),
                                        color = BlancoTexto
                                    )
                                }
                            )


                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.ShoppingCart,
                                        contentDescription = "Tienda",
                                        tint = VerdeNeon
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.navegacion_tienda),
                                        color = BlancoTexto
                                    )
                                }
                            )
                        }
                    },
                    containerColor = NegroFondo
                ) { padding ->

                    Box(modifier = Modifier.padding(padding)) {
                        when (selectedTab) {
                            0 -> CuentaScreen(usuarioVM)
                            1 -> TiendaScreen(productoVM, carritoVM, usuarioVM)
                        }
                    }
                }
            }
        }
    }
}
