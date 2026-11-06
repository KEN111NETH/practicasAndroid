
package com.example.practicasandroid.practicas.carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ProductoCarrito(
    val nombre: String,
    val precio: Double,
    val cantidad: Int = 0
)

@Composable
fun CarritoScreen() {
    val productos = remember {
        mutableStateListOf(
            ProductoCarrito("Tortillas", 24.0),
            ProductoCarrito("Leche", 27.50),
            ProductoCarrito("Huevo", 52.0),
            ProductoCarrito("Frijol", 38.0)
        )
    }

    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var pedidoConfirmado by remember { mutableStateOf(false) }

    val totalProductos = productos.sumOf { it.cantidad }
    val subtotal = productos.sumOf { it.precio * it.cantidad }
    val descuento = if (subtotal >= 200) subtotal * 0.10 else 0.0
    val subtotalConDescuento = subtotal - descuento

    val envio = when {
        totalProductos == 0 -> 0.0
        subtotalConDescuento >= 150 -> 0.0
        else -> 25.0
    }

    val total = subtotalConDescuento + envio

    fun cambiarCantidad(producto: ProductoCarrito, nuevaCantidad: Int) {
        val indice = productos.indexOf(producto)

        if (indice != -1 && nuevaCantidad in 0..10) {
            productos[indice] = producto.copy(cantidad = nuevaCantidad)
            pedidoConfirmado = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Don Pepe - Mi carrito",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(productos, key = { it.nombre }) { producto ->
                ProductoItem(
                    producto = producto,
                    onCantidadChange = { nuevaCantidad ->
                        cambiarCantidad(producto, nuevaCantidad)
                    }
                )
            }
        }

        Divider()

        Spacer(modifier = Modifier.height(12.dp))

        ResumenCarrito(
            cantidad = totalProductos,
            subtotal = subtotal,
            descuento = descuento,
            envio = envio,
            total = total
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { mostrarConfirmacion = true },
            enabled = totalProductos > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar compra")
        }

        OutlinedButton(
            onClick = {
                productos.indices.forEach { indice ->
                    productos[indice] = productos[indice].copy(cantidad = 0)
                }
                pedidoConfirmado = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vaciar carrito")
        }

        if (pedidoConfirmado) {
            Text(
                text = "¡Compra realizada correctamente!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Confirmar compra") },
            text = {
                Text("¿Deseas realizar tu compra por $${"%.2f".format(total)}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                        pedidoConfirmado = true

                        productos.indices.forEach { indice ->
                            productos[indice] =
                                productos[indice].copy(cantidad = 0)
                        }
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarConfirmacion = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ProductoItem(
    producto: ProductoCarrito,
    onCantidadChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )

                Text("$${"%.2f".format(producto.precio)}")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onCantidadChange(producto.cantidad - 1)
                    },
                    enabled = producto.cantidad > 0
                ) {
                    Text("-")
                }

                Text("${producto.cantidad}")

                IconButton(
                    onClick = {
                        onCantidadChange(producto.cantidad + 1)
                    },
                    enabled = producto.cantidad < 10
                ) {
                    Text("+")
                }
            }
        }
    }
}

@Composable
fun ResumenCarrito(
    cantidad: Int,
    subtotal: Double,
    descuento: Double,
    envio: Double,
    total: Double
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text("Productos: $cantidad")
        Text("Subtotal: $${"%.2f".format(subtotal)}")
        Text("Descuento: -$${"%.2f".format(descuento)}")
        Text("Envío: $${"%.2f".format(envio)}")

        HorizontalDivider()

        Text(
            text = "Total: $${"%.2f".format(total)}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
