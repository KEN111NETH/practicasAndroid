
package com.example.practicasandroid.practicas.cuestionario

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class EstadoQuiz {
    INICIO, QUIZ, RESULTADO
}

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int
)

@Composable
fun QuizScreen() {
    val preguntas = remember {
        listOf(
            Pregunta(
                "¿Qué palabra se utiliza para declarar una variable inmutable en Kotlin?",
                listOf("var", "val", "fun", "class"),
                1
            ),
            Pregunta(
                "¿Qué función imprime un mensaje en Kotlin?",
                listOf("print()", "input()", "read()", "show()"),
                0
            ),
            Pregunta(
                "¿Qué tipo de dato representa un número entero?",
                listOf("String", "Boolean", "Int", "Double"),
                2
            ),
            Pregunta(
                "¿Qué estructura permite repetir instrucciones?",
                listOf("if", "when", "for", "val"),
                2
            ),
            Pregunta(
                "¿Qué anotación identifica una función de interfaz en Compose?",
                listOf("@Preview", "@Composable", "@Override", "@Entity"),
                1
            )
        )
    }

    var nombre by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf(EstadoQuiz.INICIO) }
    var indicePregunta by remember { mutableIntStateOf(0) }
    var respuestaSeleccionada by remember { mutableIntStateOf(-1) }
    var puntuacion by remember { mutableIntStateOf(0) }
    var respuestaConfirmada by remember { mutableStateOf(false) }

    when (estado) {
        EstadoQuiz.INICIO -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Quiz de programación",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Ingresa tu nombre para comenzar.")

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del jugador") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        estado = EstadoQuiz.QUIZ
                    },
                    enabled = nombre.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Comenzar quiz")
                }
            }
        }

        EstadoQuiz.QUIZ -> {
            val pregunta = preguntas[indicePregunta]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    "Jugador: $nombre",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    "Pregunta ${indicePregunta + 1} de ${preguntas.size}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                LinearProgressIndicator(
                    progress = {
                        (indicePregunta + 1).toFloat() / preguntas.size
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    pregunta.texto,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(20.dp))

                pregunta.opciones.forEachIndexed { index, opcion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                respuestaConfirmada &&
                                        index == pregunta.respuestaCorrecta ->
                                    MaterialTheme.colorScheme.primaryContainer

                                respuestaConfirmada &&
                                        index == respuestaSeleccionada ->
                                    MaterialTheme.colorScheme.errorContainer

                                respuestaSeleccionada == index ->
                                    MaterialTheme.colorScheme.secondaryContainer

                                else ->
                                    MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),
                        onClick = {
                            if (!respuestaConfirmada) {
                                respuestaSeleccionada = index
                            }
                        }
                    ) {
                        Text(
                            text = opcion,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (respuestaConfirmada) {
                    Text(
                        text = if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                            "¡Respuesta correcta!"
                        } else {
                            "Respuesta incorrecta. La correcta es: " +
                                    pregunta.opciones[pregunta.respuestaCorrecta]
                        },
                        color = if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        if (!respuestaConfirmada) {
                            respuestaConfirmada = true

                            if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                                puntuacion++
                            }
                        } else {
                            if (indicePregunta < preguntas.lastIndex) {
                                indicePregunta++
                                respuestaSeleccionada = -1
                                respuestaConfirmada = false
                            } else {
                                estado = EstadoQuiz.RESULTADO
                            }
                        }
                    },
                    enabled = respuestaConfirmada || respuestaSeleccionada != -1,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (respuestaConfirmada) {
                            if (indicePregunta == preguntas.lastIndex) {
                                "Ver resultados"
                            } else {
                                "Siguiente pregunta"
                            }
                        } else {
                            "Confirmar respuesta"
                        }
                    )
                }
            }
        }

        EstadoQuiz.RESULTADO -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "¡Quiz terminado!",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Jugador: $nombre")

                Text(
                    "Puntuación: $puntuacion / ${preguntas.size}",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    when (puntuacion) {
                        5 -> "¡Excelente! Dominas estos conceptos."
                        4 -> "¡Muy bien! Casi perfecto."
                        3 -> "Buen trabajo, sigue practicando."
                        else -> "Sigue estudiando, puedes mejorar."
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        indicePregunta = 0
                        respuestaSeleccionada = -1
                        puntuacion = 0
                        respuestaConfirmada = false
                        estado = EstadoQuiz.INICIO
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver a jugar")
                }
            }
        }
    }
}
