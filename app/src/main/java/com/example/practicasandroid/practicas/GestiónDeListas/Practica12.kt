
package com.example.practicasandroid.practicas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Practica12() {
    var texto by remember { mutableStateOf("") }
    var elementos by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Lista de elementos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Nuevo elemento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (texto.isNotBlank()) {
                    elementos = elementos + texto
                    texto = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn {
            itemsIndexed(elementos) { index, elemento ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(elemento)

                    TextButton(
                        onClick = {
                            elementos = elementos.filterIndexed { i, _ ->
                                i != index
                            }
                        }
                    ) {
                        Text("Eliminar")
                    }
                }
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { elementos = emptyList() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar todo")
        }
    }
}
