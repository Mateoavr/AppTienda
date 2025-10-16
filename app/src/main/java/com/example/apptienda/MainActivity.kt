package com.example.apptienda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.apptienda.ui.theme.AppTiendaTheme
import com.example.apptienda.ui.theme.FormScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Se aplica el tema personalizado de la tienda
            AppTiendaTheme {
                // Surface es el contenedor base con el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí se llama a la pantalla principal de la aplicación
                    FormScreen()
                }
            }
        }
    }
}