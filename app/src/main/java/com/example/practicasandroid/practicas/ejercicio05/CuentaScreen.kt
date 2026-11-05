
package com.example.practicasandroid.practicas.ejercicio05

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CuentaScreen() {
    var montoTexto by remember { mutableStateOf("500") }
    var propina by remember { mutableFloatStateOf(15f) }
    var personas by remember { mutableIntStateOf(2) }
    var redondear by remember { mutableStateOf(false) }

    val monto = montoTexto.toDoubleOrNull() ?: 0.0
    val propinaTotal = monto * (propina / 100)
    val total = monto + propinaTotal
    val totalPorPersona = total / personas

    val pagoIndividual = if (redondear) {
        kotlin.math.ceil(totalPorPersona)
    } else {
        totalPorPersona
    }

    val totalRedondeado = pagoIndividual * personas
    val diferencia = totalRedondeado - total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dividir cuenta",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = montoTexto,
            onValueChange = { montoTexto = it },
            label = { Text("Monto de la cuenta") },
            prefix = { Text("$") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            text = "Propina: ${propina.toInt()}%",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(10f, 15f, 20f).forEach { porcentaje ->
                FilterChip(
                    selected = propina == porcentaje,
                    onClick = { propina = porcentaje },
                    label = { Text("${porcentaje.toInt()}%") }
                )
            }
        }

        Slider(
            value = propina,
            onValueChange = { propina = it },
            valueRange = 0f..30f
        )

        Text(
            text = "Número de personas: $personas",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { personas-- },
                enabled = personas > 1
            ) {
                Text("-")
            }

            Text(
                text = "$personas",
                modifier = Modifier.padding(horizontal = 24.dp),
                fontSize = 20.sp
            )

            OutlinedButton(
                onClick = { personas++ },
                enabled = personas < 20
            ) {
                Text("+")
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Redondear pago individual")
            Switch(
                checked = redondear,
                onCheckedChange = { redondear = it }
            )
        }

        HorizontalDivider()

        Text("Resumen de la cuenta", style = MaterialTheme.typography.titleLarge)

        Text("Consumo: $${"%.2f".format(monto)}")
        Text("Propina: $${"%.2f".format(propinaTotal)}")
        Text("Total: $${"%.2f".format(total)}")

        HorizontalDivider()

        Text(
            text = "Pago por persona: $${"%.2f".format(pagoIndividual)}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        if (redondear) {
            Text(
                text = "Diferencia por redondeo: $${"%.2f".format(diferencia)}"
            )
        }
    }
}
