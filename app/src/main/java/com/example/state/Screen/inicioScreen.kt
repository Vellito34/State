package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun quitar(can: Int): Boolean {
    if (can > 0) {
        return true
    }
    return false
}

fun poner(can: Int): Boolean {
    if (can == 10) {
        return false
    }
    return true
}

fun suma(p: List<Producto>, dato: String): Double {
    var suma = 0.00
    p.forEach { pro ->
        when (dato) {
            "Cantidad" -> suma += pro.cantidad
            "Subtotal" -> suma += pro.cantidad * pro.precio
        }
    }
    return suma
}

fun descuento(double: Double): Double {
    if (double >= 200) {
        return ((double * 0.1))
    }
    return 0.00
}

fun envio(double: Double): String {
    if (double >= 150) {
        return "GRATIS"
    } else if (double != 0.00) {
        return "25"
    }
    return "0"
}

fun total(sub: Double, envio: String, descuento: Double): Double {
    when (envio) {
        "GRATIS" -> return sub - descuento
        else -> return sub + envio.toDouble() - descuento
    }
}

@Composable
fun InicioScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    var Inventario = rememberSaveable {
        mutableListOf<Producto>(
            Producto("Tortillas 1kg", 24.00, 0),
            Producto("Leche 1L", 27.50, 0),
            Producto("Huevo 12 pz", 52.00, 0),
            Producto("Frijoles 1kg", 38.00, 0)
        )
    }
    var vaciar by rememberSaveable { mutableStateOf(false) }
    var confirmar by rememberSaveable { mutableStateOf(false) }
    if (confirmar) {
        confirmar = ScreenConfirmar(confirmar, Inventario)
    } else {
        LazyColumn(modifier = modifier, contentPadding = PaddingValues(24.dp)) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Tienda Don Pepe", fontSize = 20.sp)
                    Button(onClick = {}, shape = CircleShape, modifier = Modifier.size(30.dp)) {
                        Text(
                            "${suma(Inventario, "Cantidad").toInt()}"
                        )
                    }
                }
                Spacer(Modifier.padding(10.dp))
            }
            item {
                Inventario.forEach { p ->
                    if (vaciar) {
                        p.cantidad = 0
                        vaciar = true
                    }
                    p.cantidad = Productos(modifier = Modifier.fillMaxWidth(), producto = p, vaciar)
                }
                Spacer(Modifier.padding(10.dp))
            }
            item {
                resumen(modifier = Modifier.fillMaxSize(), Inventario)
            }
            item {
                Spacer(Modifier.padding(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            Inventario.forEach { p ->
                                p.cantidad = 0

                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.30f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black

                        )
                    ) { Text(text = "Vaciar") }

                    Button(
                        onClick = { confirmar = true },
                        modifier = Modifier.fillMaxWidth(.90f)
                    ) { Text(text = "Confirmar") }

                }

            }
        }
    }
}

@Composable
fun ScreenConfirmar(activo: Boolean, lista: List<Producto>): Boolean {
    var confirmar by rememberSaveable { mutableStateOf(activo) }
    Card(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            resumen(modifier = Modifier.fillMaxHeight(0.5f), lista)

            Button(onClick = {
                confirmar = false
            }) { Text(text = "Confirmar", textAlign = TextAlign.Center) }
        }
    }
    return confirmar
}


@Composable
fun Productos(modifier: Modifier, producto: Producto, vaciar: Boolean): Int {

    Column() {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = "${producto.nombre}", fontWeight = FontWeight.Bold)
                Text(text = "$${producto.precio} c/u", fontWeight = FontWeight.Light)
            }
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { producto.cantidad-- },
                    enabled = quitar(producto.cantidad),
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) { Text(text = "-") }
                Spacer(Modifier.padding(5.dp))
                Text(text = "${producto.cantidad}")

                Spacer(Modifier.padding(5.dp))
                Button(
                    onClick = { producto.cantidad++ },
                    enabled = poner(producto.cantidad),
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) { Text("+") }
            }
        }
        Spacer(Modifier.padding(20.dp))

    }
    return producto.cantidad;
}

@Composable
fun resumen(modifier: Modifier = Modifier, lista: List<Producto>): Int {
    Card(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Articulo", fontWeight = FontWeight.Light)
                Text(text = "${suma(lista, "Cantidad").toInt()}", fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal", fontWeight = FontWeight.Light)
                Text(text = "$${suma(lista, "Subtotal")}", fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Descuento 10 %", fontWeight = FontWeight.Light)
                Text(
                    text = "$-${descuento(suma(lista, "Subtotal"))}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Envio", fontWeight = FontWeight.Light)
                Text(
                    text = "${envio(suma(lista, "Subtotal"))}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            }
            Spacer(Modifier.padding(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "TOTAL", fontWeight = FontWeight.Bold, fontSize = 40.sp)
                Text(
                    text = "${
                        total(
                            sub = suma(lista, "Subtotal"),
                            descuento = descuento(suma(lista, "Subtotal")),
                            envio = envio(suma(lista, "Subtotal"))
                        )
                    }",
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontSize = 30.sp
                )
            }


        }
    }
    return suma(lista, "Cantidad").toInt()
}