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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
fun InicioScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: InicioViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.mostrarConfirmacion) {
        ScreenConfirmar(state = state, onConfirmar = viewModel::cerrarConfirmacion)
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
                        Text("${state.totalArticulos}")
                    }
                }
                Spacer(Modifier.padding(10.dp))
            }

            item {
                state.productos.forEach { producto ->
                    ProductoItem(
                        state = state,
                        producto = producto,
                        onMenos = { viewModel.quitar(producto.nombre) },
                        onMas = { viewModel.incrementar(producto.nombre) }
                    )
                }
            }

            item {
                Resumen(state = state, modifier = Modifier.fillMaxWidth())
            }

            item {
                Spacer(Modifier.padding(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = viewModel::vaciar,
                        modifier = Modifier.fillMaxWidth(0.30f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) { Text("Vaciar") }

                    Button(
                        onClick = viewModel::abrirConfirmacion,
                        modifier = Modifier.fillMaxWidth(0.65f)
                    ) { Text("Confirmar") }
                }
            }
        }
    }
}

@Composable
fun ScreenConfirmar(state: InicioUiState, onConfirmar: () -> Unit) {
    Card(modifier = Modifier
        .padding(50.dp)
        .fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Resumen(state = state, modifier = Modifier.fillMaxWidth())
            Button(onClick = onConfirmar) { Text("Confirmar") }
        }
    }
}

@Composable
fun ProductoItem(
    state: InicioUiState,
    producto: Producto,
    onMenos: () -> Unit,
    onMas: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(producto.nombre, fontWeight = FontWeight.Bold)
                Text("$${producto.precio} c/u", fontWeight = FontWeight.Light)
            }
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onMenos,
                    enabled = producto.cantidad > 0,
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) { Text("-") }
                Spacer(Modifier.padding(5.dp))
                Text("${producto.cantidad}")
                Spacer(Modifier.padding(5.dp))
                Button(
                    onClick = onMas,
                    enabled = producto.cantidad < state.MAX,
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) { Text("+") }
            }
        }
        Spacer(Modifier.padding(20.dp))
    }
}

@Composable
fun Resumen(state: InicioUiState, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(Modifier.padding(24.dp)) {
            FilaResumen("Artículos", "${state.totalArticulos}")
            FilaResumen("Subtotal", "$${"%.2f".format(state.subtotal)}")
            FilaResumen("Descuento 10 %", "-$${"%.2f".format(state.descuento)}", Color.Green)
            FilaResumen(
                "Envío",
                if (state.envioGratis) "GRATIS" else "$${"%.2f".format(state.envio)}",
                Color.Green
            )
            Spacer(Modifier.padding(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("TOTAL", fontWeight = FontWeight.Bold, fontSize = 40.sp)
                Text(
                    "$${"%.2f".format(state.total)}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontSize = 30.sp
                )
            }
        }
    }
}

@Composable
private fun FilaResumen(
    etiqueta: String,
    valor: String,
    colorValor: Color = Color.Unspecified
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(etiqueta, fontWeight = FontWeight.Light)
        Text(valor, fontWeight = FontWeight.Bold, color = colorValor)
    }
}