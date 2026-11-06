package com.example.practicasandroid.practicas.contador

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicasandroid.practicas.contador.Components.Boton

@Composable
fun ContadorScreen() {

    var contador by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Contador",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = contador.toString(),
            fontSize = 50.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Boton(
            text = "Incrementar",
            onClick = { contador++ }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Boton(
            text = "Disminuir",
            onClick = { contador-- }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Boton(
            text = "Reiniciar",
            onClick = { contador = 0 }
        )
    }
}