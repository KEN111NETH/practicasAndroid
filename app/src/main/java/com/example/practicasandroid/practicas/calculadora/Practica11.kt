
package com.example.practicasandroid.practicas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Practica11() {
    var numero by remember { mutableStateOf("") }
    var anterior by remember { mutableStateOf(0.0) }
    var operador by remember { mutableStateOf("") }
    var nuevoNumero by remember { mutableStateOf(false) }

    fun presionar(valor: String) {
        when (valor) {
            "C" -> {
                numero = ""
                anterior = 0.0
                operador = ""
            }
            "+", "-", "×", "÷" -> {
                anterior = numero.toDoubleOrNull() ?: 0.0
                operador = valor
                nuevoNumero = true
            }
            "=" -> {
                val actual = numero.toDoubleOrNull() ?: 0.0
                val resultado = when (operador) {
                    "+" -> anterior + actual
                    "-" -> anterior - actual
                    "×" -> anterior * actual
                    "÷" -> if (actual != 0.0) anterior / actual else null
                    else -> actual
                }
                numero = resultado?.toString() ?: "Error"
                operador = ""
                nuevoNumero = true
            }
            else -> {
                if (numero == "Error" || nuevoNumero) {
                    numero = valor
                    nuevoNumero = false
                } else {
                    numero += valor
                }
            }
        }
    }

    val botones = listOf(
        listOf("7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadora",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = numero.ifEmpty { "0" },
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        botones.forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fila.forEach { boton ->
                    Button(
                        onClick = { presionar(boton) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(boton, fontSize = 22.sp)
                    }
                }
            }
        }
    }
}
