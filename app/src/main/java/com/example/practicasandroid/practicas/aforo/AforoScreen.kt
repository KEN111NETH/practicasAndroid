
package com.example.practicasandroid.practicas.aforo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AforoScreen() {
    var personas by remember { mutableIntStateOf(18) }
    var visitas by remember { mutableIntStateOf(35) }

    val capacidad = 50
    val porcentaje = personas.toFloat() / capacidad
    val colorAforo = when {
        porcentaje >= 1f -> Color.Red
        porcentaje >= 0.7f -> Color(0xFFFF9800)
        else -> Color(0xFF4CAF50)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Biblioteca Central",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "$personas / $capacidad",
            style = MaterialTheme.typography.displayLarge,
            color = colorAforo
        )

        Text("Personas dentro")

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { porcentaje },
            modifier = Modifier.fillMaxWidth(),
            color = colorAforo,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Text("${(porcentaje * 100).toInt()}% de capacidad")

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (personas < capacidad) {
                        personas++
                        visitas++
                    }
                },
                enabled = personas < capacidad
            ) {
                Text("+ Entrada")
            }

            Button(
                onClick = {
                    if (personas > 0) {
                        personas--
                    }
                },
                enabled = personas > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("- Salida")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Visitas del día: $visitas",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                personas = 0
                visitas = 0
            }
        ) {
            Text("Reiniciar día")
        }

        OutlinedButton(
            onClick = {
                personas = (personas + 5).coerceAtMost(capacidad)
                visitas += minOf(5, capacidad - (personas - minOf(5, capacidad)))
            }
        ) {
            Text("+5 personas")
        }
    }
}
