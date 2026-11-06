
package com.example.practicasandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practicasandroid.practicas.contador.ContadorScreen

// Práctica 01
import com.example.practicasandroid.practicas.aforo.AforoScreen

// Práctica 02
import com.example.practicasandroid.practicas.lectura.LecturaScreen

// Práctica 03
import com.example.practicasandroid.practicas.inscripcion.InscripcionScreen

// Práctica 04
import com.example.practicasandroid.practicas.carrito.CarritoScreen

// Práctica 05
import com.example.practicasandroid.practicas.cuenta.CuentaScreen

// Práctica 06
import com.example.practicasandroid.practicas.tareas.TareasScreen

// Práctica 07
import com.example.practicasandroid.practicas.login.LoginScreen

// Práctica 08
import com.example.practicasandroid.practicas.resena.ResenaScreen

// Práctica 09
import com.example.practicasandroid.practicas.presupuestos.PresupuestoScreen

// Práctica 10
import com.example.practicasandroid.practicas.cuestionario.QuizScreen

// Práctica 11
import com.example.practicasandroid.practicas.Practica11

// Práctica 12
import com.example.practicasandroid.practicas.Practica12

// Práctica 13
import com.example.practicasandroid.practicas.Practica13

// Práctica 14
import com.example.practicasandroid.practicas.contador.ContadorScreen
// Práctica 15
import com.example.practicasandroid.practicas.suma.SumaScreen
// Práctica 16
import com.example.practicasandroid.practicas.ejercicio16.Screens.SiglasScreen

@Composable
fun PracticasApp() {

    var practicaSeleccionada by remember {
        mutableIntStateOf(0)
    }

    val practicas = listOf(
        "01. Control de aforo",
        "02. Lectura",
        "03. Inscripción",
        "04. Carrito de compras",
        "05. Cuenta",
        "06. Lista de tareas",
        "07. Inicio de sesión",
        "08. Reseñas",
        "09. Presupuesto",
        "10. Quiz de programación",
        "11. Calculadora",
        "12. Gestión de listas",
        "13. Registro de personas",
        "14. Contador",
        "15. Suma",
        "16. Número de siglas"
    )

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            if (practicaSeleccionada == 0) {

                Text(
                    text = "Mis prácticas Android",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(practicas) { index, nombre ->

                        Button(
                            onClick = {
                                practicaSeleccionada = index + 1
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(nombre)
                        }
                    }
                }

            } else {

                TextButton(
                    onClick = {
                        practicaSeleccionada = 0
                    }
                ) {
                    Text("← Volver al menú")
                }

                when (practicaSeleccionada) {

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
                    11 -> Practica11()
                    12 -> Practica12()
                    13 -> Practica13()
                    14 -> ContadorScreen()
                    15 -> SumaScreen()
                    16 -> SiglasScreen()
                }
            }
        }
    }
}
