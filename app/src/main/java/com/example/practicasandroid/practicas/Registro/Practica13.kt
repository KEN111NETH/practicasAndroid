
package com.example.practicasandroid.practicas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Persona(
    val nombre: String,
    val edad: String,
    val correo: String,
    val telefono: String
)

@Composable
fun Practica13() {

    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    var personas by remember {
        mutableStateOf(listOf<Persona>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Registro de personas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (
                    nombre.isNotBlank() &&
                    edad.isNotBlank() &&
                    correo.isNotBlank() &&
                    telefono.isNotBlank()
                ) {
                    personas = personas + Persona(
                        nombre,
                        edad,
                        correo,
                        telefono
                    )

                    nombre = ""
                    edad = ""
                    correo = ""
                    telefono = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar persona")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Personas registradas: ${personas.size}",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn {
            itemsIndexed(personas) { index, persona ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Nombre: ${persona.nombre}")
                        Text("Edad: ${persona.edad}")
                        Text("Correo: ${persona.correo}")
                        Text("Teléfono: ${persona.telefono}")

                        TextButton(
                            onClick = {
                                personas = personas.filterIndexed { i, _ ->
                                    i != index
                                }
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}
