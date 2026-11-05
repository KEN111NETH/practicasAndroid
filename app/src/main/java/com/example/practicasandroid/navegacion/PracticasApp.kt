
package com.example.practicasandroid.navegacion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.practicasandroid.practicas.ejercicio01.AforoScreen
import com.example.practicasandroid.practicas.ejercicio02.LecturaScreen
import com.example.practicasandroid.practicas.ejercicio03.InscripcionScreen
import com.example.practicasandroid.practicas.ejercicio04.CarritoScreen
import com.example.practicasandroid.practicas.ejercicio05.CuentaScreen
import com.example.practicasandroid.practicas.ejercicio06.TareasScreen
import com.example.practicasandroid.practicas.ejercicio07.LoginScreen
import com.example.practicasandroid.practicas.ejercicio08.ResenaScreen
import com.example.practicasandroid.practicas.ejercicio09.PresupuestoScreen
import com.example.practicasandroid.practicas.ejercicio10.QuizScreen

@Composable
fun PracticasApp() {
    var ejercicioSeleccionado by remember {
        mutableIntStateOf(0)
    }

    val ejercicios = listOf(
        "01. Control de aforo",
        "02. Ajustes de lectura",
        "03. Inscripción a talleres",
        "04. Carrito de compras",
        "05. Dividir cuenta",
        "06. Gestor de tareas",
        "07. Clínica Vida - Login",
        "08. Reseñas de restaurantes",
        "09. Presupuesto mensual",
        "10. Quiz de programación"
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (ejercicioSeleccionado == 0) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Prácticas Android",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = "Jetpack Compose - 10 ejercicios de estado",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(ejercicios.withIndex().toList()) { (index, nombre) ->
                            Button(
                                onClick = {
                                    ejercicioSeleccionado = index + 1
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(nombre)
                            }
                        }
                    }
                }

            } else {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    TextButton(
                        onClick = {
                            ejercicioSeleccionado = 0
                        }
                    ) {
                        Text("← Volver al menú")
                    }
                }

                HorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when (ejercicioSeleccionado) {
                        1 -> AforoScreen()
                        2 -> LecturaScreen()
                        3 -> InscripcionScreen()
                        4 -> CarritoScreen()
                        5 -> CuentaScreen()
                        6 -> TareasScreen()
                        7 -> LoginScreen()
                        8 -> ResenaScreen()
                        9 -> PresupuestoScreen()
                        10 -> QuizScreen()
                    }
                }
            }
        }
    }
}
