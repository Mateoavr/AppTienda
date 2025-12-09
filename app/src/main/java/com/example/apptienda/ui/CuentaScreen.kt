package com.example.apptienda.ui.theme

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptienda.R
import com.example.apptienda.model.local.Compra
import com.example.apptienda.viewmodel.UsuarioViewModel



@Composable
fun CuentaScreen(usuarioVM: UsuarioViewModel) {
    val usuario by usuarioVM.usuario.collectAsState()
    val error by usuarioVM.error.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaNacimientoStr by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val historial by usuarioVM.historial.collectAsState()

    val NegroFondo = Color(0xFF121212L)
    val VerdeNeon = Color(0xFF39FF14L)
    val BlancoTexto = Color.White
    val GrisClaroTexto = Color.LightGray
    val GrisOscuroCard = Color(0xFF1E1E1EL)
    val GrisHistorial = Color(0xFF2C2C2C)

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            Toast.makeText(
                context,
                "Foto seleccionada: ${uri.lastPathSegment}",
                Toast.LENGTH_LONG
            ).show()

        }
    }


    Column(
        modifier = Modifier
            .background(NegroFondo)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Mi Cuenta",
            color = VerdeNeon,
            fontSize = 24.sp
        )

        Spacer(Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = GrisOscuroCard),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Image(
                    painter = painterResource(id = R.drawable.tu_imagen),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 16.dp)
                        .clip(CircleShape)
                        .then(
                            if (usuario != null) {
                                Modifier.clickable { galleryLauncher.launch("image/*") }
                            } else Modifier
                        )
                )


                if (usuario == null) {
                    Text("Regístrate o Ingresa", color = BlancoTexto, fontSize = 18.sp)
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            usuarioVM.limpiarError()
                        },
                        label = { Text("Nombre Completo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(Modifier.height(8.dp))


                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                            usuarioVM.limpiarError()
                        },
                        label = { Text("Correo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(Modifier.height(8.dp))


                    OutlinedTextField(
                        value = fechaNacimientoStr,
                        onValueChange = {
                            fechaNacimientoStr = it
                            usuarioVM.limpiarError()
                        },
                        label = { Text("Fecha Nacimiento (yyyy-MM-dd)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(Modifier.height(8.dp))


                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = {
                            contrasena = it
                            usuarioVM.limpiarError()
                        },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            val description = if (passwordVisible) "Ocultar" else "Mostrar"
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description, tint = VerdeNeon)
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = BlancoTexto,
                            unfocusedTextColor = GrisClaroTexto,
                            cursorColor = VerdeNeon,
                            focusedBorderColor = VerdeNeon,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    error?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(Modifier.height(16.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                usuarioVM.registrar(
                                    nombre,
                                    correo,
                                    contrasena,
                                    fechaNacimientoStr
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text("Registrar", color = NegroFondo)
                        }

                        Button(
                            onClick = {
                                usuarioVM.login(correo, contrasena)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                        ) {
                            Text("Ingresar", color = NegroFondo)
                        }
                    }


                } else {
                    val edadCalculada = usuarioVM.calculaEdad(usuario!!.fechaNacimiento)

                    Text("¡Bienvenido, ${usuario!!.nombre}!", color = BlancoTexto, fontSize = 20.sp)
                    Text("Correo: ${usuario!!.correo}", color = GrisClaroTexto, fontSize = 14.sp)
                    Text("Edad: $edadCalculada", color = GrisClaroTexto, fontSize = 14.sp)

                    Spacer(Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(GrisHistorial, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "HISTORIAL COMPRAS",
                            color = VerdeNeon,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        historial.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(item.nombre, color = BlancoTexto, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                    Text(item.fecha, color = Color.Gray, fontSize = 12.sp)
                                }
                                Text(item.precio, color = GrisClaroTexto, fontSize = 14.sp)
                            }

                            if (index < historial.size - 1) {
                                HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val numeroSoporte = "+56959483719"
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://wa.me/$numeroSoporte")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Icon(Icons.Default.SupportAgent, contentDescription = null, tint = BlancoTexto)
                        Spacer(Modifier.width(8.dp))
                        Text("Soporte", color = BlancoTexto)
                    }

                    Spacer(Modifier.height(8.dp))


                    Button(
                        onClick = {
                            usuarioVM.logout()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeNeon)
                    ) {
                        Text("Cerrar Sesión", color = NegroFondo)
                    }
                }
            }
        }
    }
}