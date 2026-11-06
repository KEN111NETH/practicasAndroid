
package com.example.practicasandroid.practicas.suma
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicasandroid.practicas.suma.Components.Boton_Suma

@Composable
fun SumaScreen(modifier: Modifier = Modifier) {

    var val1 by remember { mutableStateOf("") }
    var val2 by remember { mutableStateOf("") }
    var suma by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = val1,
            onValueChange = { val1 = it },
            label = { Text("Primer número") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = val2,
            onValueChange = { val2 = it },
            label = { Text("Segundo número") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$suma",
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Boton_Suma(
            text = "Sumar",
            onClick = {
                suma = (val1.toIntOrNull() ?: 0) +
                        (val2.toIntOrNull() ?: 0)
            }
        )
    }
}
