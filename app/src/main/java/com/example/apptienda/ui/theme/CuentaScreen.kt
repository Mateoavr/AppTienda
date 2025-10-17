package com.example.apptienda.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.viewmodel.UsuarioViewModel

@Composable
fun CuentaScreen(usuarioVM: UsuarioViewModel) {
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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



                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña (mínimo 6 caracteres)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                            onClick = {
                                val edadInt = edad.toIntOrNull() ?: 0
                                usuarioVM.registrar(nombre, correo, contrasena)
                            },
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

                    Text("Bienvenido, ${usuario!!.nombre}", color = BlancoTexto, fontSize = 20.sp)
                    Text("Correo: ${usuario!!.correo}", color = GrisClaroTexto, fontSize = 14.sp)

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