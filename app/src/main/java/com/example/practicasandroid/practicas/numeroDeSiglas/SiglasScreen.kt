
package com.example.practicasandroid.practicas.ejercicio16.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practicasandroid.practicas.ejercicio16.Components.Boton

@Composable
fun SiglasScreen(modifier: Modifier = Modifier) {

    var cant by remember { mutableStateOf("") }
    var repeticiones by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Ingrese la cantidad de N")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = cant,
            onValueChange = { cant = it },
            label = { Text("Cantidad") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Boton(
            text = "Imprimir",
            onClick = {
                repeticiones = (cant.toIntOrNull() ?: 0).coerceIn(0, 100)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(repeticiones) {
                Text(text = "N de Niño")
            }
        }
    }
}
