
package com.example.practicasandroid.practicas.presupuestos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Gasto(
    val id: Int,
    val concepto: String,
    val monto: Double,
    val categoria: String
)

@Composable
fun PresupuestoScreen() {
    var ingresoTexto by remember { mutableStateOf("4500") }
    var concepto by remember { mutableStateOf("") }
    var montoTexto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("Comida") }
    var mostrarCategorias by remember { mutableStateOf(false) }
    var siguienteId by remember { mutableIntStateOf(1) }

    val gastos = remember {
        mutableStateListOf<Gasto>()
    }

    val categorias = listOf(
        "Comida",
        "Transporte",
        "Servicios",
        "Entretenimiento",
        "Otros"
    )

    val ingreso = ingresoTexto.toDoubleOrNull() ?: 0.0
    val totalGastos = gastos.sumOf { it.monto }
    val saldo = ingreso - totalGastos

    val porcentaje = if (ingreso > 0) {
        totalGastos / ingreso
    } else {
        0.0
    }

    val categoriaMayor = gastos
        .groupBy { it.categoria }
        .mapValues { grupo -> grupo.value.sumOf { it.monto } }
        .maxByOrNull { it.value }

    val montoValido = montoTexto.toDoubleOrNull()?.let {
        it > 0
    } ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi presupuesto mensual",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = ingresoTexto,
            onValueChange = { ingresoTexto = it },
            label = { Text("Ingreso mensual") },
            prefix = { Text("$") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Resumen financiero", style = MaterialTheme.typography.titleLarge)

                Text("Ingresos: $${"%.2f".format(ingreso)}")
                Text("Gastos: $${"%.2f".format(totalGastos)}")

                Text(
                    text = "Saldo disponible: $${"%.2f".format(saldo)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (saldo >= 0) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )

                Text("Presupuesto utilizado: ${(porcentaje * 100).toInt()}%")

                LinearProgressIndicator(
                    progress = { porcentaje.toFloat().coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth()
                )

                when {
                    ingreso <= 0 -> {
                        Text(
                            "Ingresa un ingreso válido.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    porcentaje > 1 -> {
                        Text(
                            "¡Alerta! Superaste tu presupuesto.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    porcentaje >= 0.8 -> {
                        Text(
                            "¡Cuidado! Has utilizado el 80% de tu presupuesto.",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                if (categoriaMayor != null) {
                    Text(
                        "Mayor gasto: ${categoriaMayor.key} " +
                                "($${"%.2f".format(categoriaMayor.value)})"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Registrar gasto",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = concepto,
            onValueChange = { concepto = it },
            label = { Text("Concepto del gasto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = montoTexto,
            onValueChange = { montoTexto = it },
            label = { Text("Monto") },
            prefix = { Text("$") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Box {
            OutlinedButton(
                onClick = { mostrarCategorias = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Categoría: $categoria")
            }

            DropdownMenu(
                expanded = mostrarCategorias,
                onDismissRequest = { mostrarCategorias = false }
            ) {
                categorias.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            categoria = opcion
                            mostrarCategorias = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val monto = montoTexto.toDoubleOrNull()

                if (concepto.isNotBlank() && monto != null && monto > 0) {
                    gastos.add(
                        Gasto(
                            id = siguienteId,
                            concepto = concepto.trim(),
                            monto = monto,
                            categoria = categoria
                        )
                    )

                    siguienteId++
                    concepto = ""
                    montoTexto = ""
                    categoria = "Comida"
                }
            },
            enabled = concepto.isNotBlank() && montoValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar gasto")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        Text(
            "Mis gastos",
            style = MaterialTheme.typography.titleLarge
        )

        if (gastos.isEmpty()) {
            Text("Todavía no has registrado gastos.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gastos, key = { it.id }) { gasto ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    gasto.concepto,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(gasto.categoria)
                            }

                            Text("$${"%.2f".format(gasto.monto)}")

                            TextButton(
                                onClick = {
                                    gastos.remove(gasto)
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
}
