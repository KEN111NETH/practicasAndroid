
package com.example.practicasandroid.practicas.lectura

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Espaciado {
    Compacto, Normal, Amplio
}

@Composable
fun LecturaScreen() {
    var tamanoFuente by remember { mutableFloatStateOf(18f) }
    var espaciado by remember { mutableStateOf(Espaciado.Normal) }
    var modoOscuro by remember { mutableStateOf(false) }
    var fuenteSerif by remember { mutableStateOf(false) }

    val interlineado = when (espaciado) {
        Espaciado.Compacto -> 20.sp
        Espaciado.Normal -> 28.sp
        Espaciado.Amplio -> 36.sp
    }

    val fondo = if (modoOscuro) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val texto = if (modoOscuro) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Ajustes de lectura",
            style = MaterialTheme.typography.headlineMedium,
            color = texto
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Tamaño de fuente: ${tamanoFuente.toInt()} sp", color = texto)

        Slider(
            value = tamanoFuente,
            onValueChange = { tamanoFuente = it },
            valueRange = 14f..32f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Espaciado entre líneas", color = texto)

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            Espaciado.values().forEachIndexed { index, opcion ->
                SegmentedButton(
                    selected = espaciado == opcion,
                    onClick = { espaciado = opcion },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = Espaciado.values().size
                    )
                ) {
                    Text(opcion.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Modo oscuro", color = texto)
            Switch(
                checked = modoOscuro,
                onCheckedChange = { modoOscuro = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fuente Serif", color = texto)
            Switch(
                checked = fuenteSerif,
                onCheckedChange = { fuenteSerif = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Vista previa",
            style = MaterialTheme.typography.titleLarge,
            color = texto
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Jetpack Compose permite crear interfaces modernas y dinámicas. Puedes personalizar el tamaño, el espaciado y el tipo de letra para mejorar tu experiencia de lectura.",
            fontSize = tamanoFuente.sp,
            lineHeight = interlineado,
            fontFamily = if (fuenteSerif) FontFamily.Serif else FontFamily.SansSerif,
            color = texto
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                tamanoFuente = 18f
                espaciado = Espaciado.Normal
                modoOscuro = false
                fuenteSerif = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restablecer ajustes")
        }
    }
}
