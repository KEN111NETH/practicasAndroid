
package com.example.practicasandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicasandroid.navegacion.PracticasApp
import com.example.practicasandroid.ui.theme.PracticasAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PracticasAndroidTheme {
                PracticasApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PracticasPreview() {
    PracticasAndroidTheme {
        PracticasApp()
    }
}
