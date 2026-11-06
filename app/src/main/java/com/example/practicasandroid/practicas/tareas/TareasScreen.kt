
package com.example.practicasandroid.practicas.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

enum class Prioridad {
    Baja, Media, Alta
}

enum class FiltroTareas {
    Todas, Pendientes, Completadas
}

data class Tarea(
    val id: Int,
    val nombre: String,
    val prioridad: Prioridad,
    val completada: Boolean = false
)

@Composable
fun TareasScreen() {
    val tareas = remember {
        mutableStateListOf(
            Tarea(1, "Estudiar Kotlin", Prioridad.Alta),
            Tarea(2, "Hacer ejercicio", Prioridad.Media),
            Tarea(3, "Entregar tarea", Prioridad.Alta),
            Tarea(4, "Ordenar habitación", Prioridad.Baja)
        )
    }

    var nombre by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf(Prioridad.Media) }
    var filtro by remember { mutableStateOf(FiltroTareas.Todas) }
    var siguienteId by remember { mutableIntStateOf(5) }
    var tareaEliminar by remember { mutableStateOf<Tarea?>(null) }

    val pendientes = tareas.count { !it.completada }
    val completadas = tareas.count { it.completada }

    val tareasFiltradas = when (filtro) {
        FiltroTareas.Todas -> tareas.toList()
        FiltroTareas.Pendientes -> tareas.filter { !it.completada }
        FiltroTareas.Completadas -> tareas.filter { it.completada }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gestor de tareas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nueva tarea") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            text = "Prioridad",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Prioridad.values().forEach { opcion ->
                FilterChip(
                    selected = prioridad == opcion,
                    onClick = { prioridad = opcion },
                    label = { Text(opcion.name) }
                )
            }
        }

        Button(
            onClick = {
                if (nombre.isNotBlank()) {
                    tareas.add(
                        Tarea(
                            id = siguienteId,
                            nombre = nombre.trim(),
                            prioridad = prioridad
                        )
                    )
                    siguienteId++
                    nombre = ""
                    prioridad = Prioridad.Media
                }
            },
            enabled = nombre.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar tarea")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Total: ${tareas.size} | Pendientes: $pendientes | Completadas: $completadas")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FiltroTareas.values().forEach { opcion ->
                FilterChip(
                    selected = filtro == opcion,
                    onClick = { filtro = opcion },
                    label = { Text(opcion.name) }
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tareasFiltradas, key = { it.id }) { tarea ->
                TareaItem(
                    tarea = tarea,
                    onCompletadaChange = { completada ->
                        val indice = tareas.indexOfFirst { it.id == tarea.id }
                        if (indice != -1) {
                            tareas[indice] =
                                tareas[indice].copy(completada = completada)
                        }
                    },
                    onEliminar = {
                        tareaEliminar = tarea
                    }
                )
            }
        }
    }

    tareaEliminar?.let { tarea ->
        AlertDialog(
            onDismissRequest = { tareaEliminar = null },
            title = { Text("Eliminar tarea") },
            text = {
                Text("¿Deseas eliminar la tarea '${tarea.nombre}'?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        tareas.removeAll { it.id == tarea.id }
                        tareaEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { tareaEliminar = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun TareaItem(
    tarea: Tarea,
    onCompletadaChange: (Boolean) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = onCompletadaChange
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tarea.nombre,
                    textDecoration = if (tarea.completada) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )

                Text(
                    text = "Prioridad: ${tarea.prioridad.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            TextButton(onClick = onEliminar) {
                Text("Eliminar")
            }
        }
    }
}
