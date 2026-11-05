
package com.example.practicasandroid.practicas.ejercicio08

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResenaScreen() {
    var comida by remember { mutableIntStateOf(0) }
    var servicio by remember { mutableIntStateOf(0) }
    var ambiente by remember { mutableIntStateOf(0) }

    var comentario by remember { mutableStateOf("") }
    var etiquetas by remember { mutableStateOf(setOf<String>()) }
    var publicada by remember { mutableStateOf(false) }

    val opciones = listOf(
        "Comida",
        "Servicio",
        "Ambiente",
        "Precio",
        "Limpieza"
    )

    val todasCalificadas =
        comida > 0 && servicio > 0 && ambiente > 0

    val promedio = if (todasCalificadas) {
        (comida + servicio + ambiente) / 3.0
    } else {
        null
    }

    val comentarioValido = comentario.length in 20..200

    val formularioValido =
        todasCalificadas && comentarioValido

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Reseña de restaurante",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Califica tu experiencia")

        RatingBar(
            titulo = "Comida",
            calificacion = comida,
            onCalificacionChange = {
                comida = it
                publicada = false
            }
        )

        RatingBar(
            titulo = "Servicio",
            calificacion = servicio,
            onCalificacionChange = {
                servicio = it
                publicada = false
            }
        )

        RatingBar(
            titulo = "Ambiente",
            calificacion = ambiente,
            onCalificacionChange = {
                ambiente = it
                publicada = false
            }
        )

        HorizontalDivider()

        Text(
            text = "Promedio de calificación",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = promedio?.let { "%.1f / 5".format(it) }
                ?: "Califica las tres categorías",
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider()

        Text(
            text = "Cuéntanos tu experiencia",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = comentario,
            onValueChange = {
                if (it.length <= 200) {
                    comentario = it
                    publicada = false
                }
            },
            label = { Text("Escribe tu comentario") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            isError = comentario.isNotEmpty() && !comentarioValido
        )

        Text(
            text = "${comentario.length}/200 caracteres",
            color = if (comentario.length >= 180) {
                Color.Red
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )

        if (comentario.isNotEmpty() && comentario.length < 20) {
            Text(
                text = "Necesitas al menos 20 caracteres.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(
            text = "Etiquetas de tu experiencia",
            style = MaterialTheme.typography.titleMedium
        )

        opciones.forEach { opcion ->
            FilterChip(
                selected = opcion in etiquetas,
                onClick = {
                    etiquetas = if (opcion in etiquetas) {
                        etiquetas - opcion
                    } else {
                        etiquetas + opcion
                    }
                    publicada = false
                },
                label = { Text(opcion) }
            )
        }

        Button(
            onClick = { publicada = true },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar reseña")
        }

        if (publicada) {
            Text(
                text = "¡Reseña publicada correctamente!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun RatingBar(
    titulo: String,
    calificacion: Int,
    onCalificacionChange: (Int) -> Unit
) {
    Column {
        Text(
            text = "$titulo: $calificacion / 5",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { estrella ->
                Text(
                    text = if (estrella <= calificacion) "★" else "☆",
                    color = Color(0xFFFFB300),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.clickable {
                        onCalificacionChange(estrella)
                    }
                )
            }
        }
    }
}
