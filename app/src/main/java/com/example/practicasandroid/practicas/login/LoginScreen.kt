
package com.example.practicasandroid.practicas.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

enum class EstadoLogin {
    INACTIVO, ERROR, BLOQUEADO, EXITO
}

@Composable
fun LoginScreen() {
    val correoCorrecto = "paciente@vida.mx"
    val contrasenaCorrecta = "Vida2025"

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var recordar by remember { mutableStateOf(false) }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var intentos by remember { mutableIntStateOf(0) }
    var estado by remember { mutableStateOf(EstadoLogin.INACTIVO) }

    if (estado == EstadoLogin.EXITO) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido a Clínica Vida!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Sesión iniciada correctamente.")
            Text("Correo: $correo")

            Text(
                text = if (recordar) "Recordarme: Sí" else "Recordarme: No"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    estado = EstadoLogin.INACTIVO
                    correo = ""
                    contrasena = ""
                    recordar = false
                    mostrarContrasena = false
                }
            ) {
                Text("Cerrar sesión")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clínica Vida",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Acceso para pacientes",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = estado != EstadoLogin.BLOQUEADO
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = estado != EstadoLogin.BLOQUEADO,
                visualTransformation = if (mostrarContrasena) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    TextButton(
                        onClick = {
                            mostrarContrasena = !mostrarContrasena
                        }
                    ) {
                        Text(if (mostrarContrasena) "Ocultar" else "Mostrar")
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = recordar,
                    onCheckedChange = { recordar = it },
                    enabled = estado != EstadoLogin.BLOQUEADO
                )
                Text("Recordarme")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (
                        correo == correoCorrecto &&
                        contrasena == contrasenaCorrecta
                    ) {
                        estado = EstadoLogin.EXITO
                    } else {
                        intentos++

                        estado = if (intentos >= 3) {
                            EstadoLogin.BLOQUEADO
                        } else {
                            EstadoLogin.ERROR
                        }
                    }
                },
                enabled = estado != EstadoLogin.BLOQUEADO &&
                        correo.isNotBlank() &&
                        contrasena.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (estado) {
                EstadoLogin.ERROR -> {
                    Text(
                        text = "Credenciales incorrectas. Intentos restantes: ${3 - intentos}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                EstadoLogin.BLOQUEADO -> {
                    Text(
                        text = "Acceso bloqueado. Alcanzaste los 3 intentos.",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text("Intentos utilizados: $intentos de 3")
                }
            }
        }
    }
}
