
package com.example.practicasandroid.practicas.inscripcion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InscripcionScreen() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var taller by rememberSaveable { mutableStateOf("Programación") }
    var aceptaPrivacidad by rememberSaveable { mutableStateOf(false) }
    var enviado by rememberSaveable { mutableStateOf(false) }
    var mostrarErrores by rememberSaveable { mutableStateOf(false) }

    val edadNumero = edad.toIntOrNull()

    val nombreValido = nombre.isNotBlank()
    val correoValido = android.util.Patterns.EMAIL_ADDRESS
        .matcher(correo)
        .matches()
    val edadValida = edadNumero != null && edadNumero in 15..100

    val formularioValido =
        nombreValido && correoValido && edadValida && aceptaPrivacidad

    if (enviado) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Inscripción exitosa!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Nombre: $nombre")
            Text("Correo: $correo")
            Text("Edad: $edad años")
            Text("Taller: $taller")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    nombre = ""
                    correo = ""
                    edad = ""
                    taller = "Programación"
                    aceptaPrivacidad = false
                    enviado = false
                    mostrarErrores = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Nueva inscripción")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Inscripción a talleres",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                isError = mostrarErrores && !nombreValido,
                singleLine = true
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = mostrarErrores && !correoValido,
                singleLine = true
            )

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it.filter { caracter -> caracter.isDigit() } },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                isError = mostrarErrores && !edadValida,
                singleLine = true
            )

            Text(
                text = "Selecciona un taller",
                style = MaterialTheme.typography.titleMedium
            )

            val talleres = listOf(
                "Programación",
                "Diseño gráfico",
                "Robótica",
                "Inteligencia artificial"
            )

            talleres.forEach { opcion ->
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = taller == opcion,
                        onClick = { taller = opcion }
                    )
                    Text(opcion)
                }
            }

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceptaPrivacidad,
                    onCheckedChange = { aceptaPrivacidad = it }
                )
                Text("Acepto las políticas de privacidad")
            }

            if (mostrarErrores && !formularioValido) {
                Text(
                    text = "Completa correctamente todos los campos y acepta las políticas.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    mostrarErrores = true
                    if (formularioValido) {
                        enviado = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Inscribirme")
            }
        }
    }
}
