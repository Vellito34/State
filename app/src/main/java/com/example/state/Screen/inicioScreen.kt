package com.example.state.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Slider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt

fun divi(n1: Double, n2: Double): Double {
    return n1 / n2
}

fun total(n1: Double, desc: Double): Double {
    return n1 + (n1 * desc)
}


@Composable
fun InicioScreen(modifier: Modifier, viewModel: DatosViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(contentPadding = PaddingValues(24.dp)) {
        item {
            Text(text = "Cuenta dividida", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.padding(20.dp))
            OutlinedTextField(
                value = state.monto,
                onValueChange = viewModel::montoChange,
                label = { Text("monto") },
                placeholder = { Text("Monto de la cuenta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = false,
                supportingText = { Text("Ingresa un monto valido") }
            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Propina")
                Row() {
                    propina(propina = state.propina, viewModel::propinaChange)
                }
            }
            Spacer(Modifier.padding(10.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Personas")

                SelectP(
                    cantidad = state.cantidad,
                    viewModel::menosCantidad,
                    viewModel::menosCantidad
                )
            }
            Spacer(Modifier.padding(5.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear por persona")
                Switch(checked = state.rendondear, onCheckedChange = viewModel::redondear)
            }
            Spacer(Modifier.padding(10.dp))

        }
        item {
            Resumen(
                propina = state.propina,
                monto = state.dinero,
                porPersona = state.porPersona,
                propinaMonto = state.propinaMonto,
                personas = state.cantidad,
                demas = state.sobrante,
                total = state.total
            )
        }

    }
}

@Composable
fun propina(propina: Double, UpPropina: (Double) -> Unit) {
    var otro by rememberSaveable { mutableStateOf(false) }
    var slider by rememberSaveable { mutableStateOf(0f) }
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(10, 15, 20).forEach { por ->
                FilterChip(
                    selected = !otro && propina == por / 100.0,
                    onClick = {
                        otro = false
                        UpPropina(por / 100.0)
                    },
                    label = { Text("$por %") }
                )
            }
            FilterChip(
                selected = otro,
                onClick = {
                    otro = true
                    UpPropina(slider.roundToInt() / 100.0)
                },
                label = { Text("Otro") }
            )
        }
        if (otro) {
            Text("${slider.roundToInt()} %")
            Slider(
                value = slider,
                onValueChange = {
                    slider = it
                    UpPropina(it.roundToInt() / 100.0)
                },
                valueRange = 0f..30f,
                steps = 29
            )
        }
    }
}

@Composable
fun SelectP(cantidad: Int, onMenos: () -> Unit, onMas: () -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onMenos,
            enabled = (cantidad > 1),
            shape = CircleShape,
            modifier = Modifier.size(50.dp)
        ) {

            Text(text = "-", textAlign = TextAlign.Center)
        }
        Spacer(Modifier.padding(5.dp))
        Text("$cantidad")
        Spacer(Modifier.padding(5.dp))
        Button(
            onClick = onMas,
            enabled = (cantidad < 20),
            shape = CircleShape,
            modifier = Modifier.size(50.dp)
        ) {
            Text(text = "+", textAlign = TextAlign.Center)
        }
    }


}

@Composable
fun Resumen(
    propina: Double,
    monto: Double,
    porPersona: Double,
    propinaMonto: Double,
    personas: Int,
    demas: Double,
    total: Double
) {

    Card(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Propina (${propina * 100} %)")
                Text("$${porPersona}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total con propina")
                Text("$${total}")
            }
            Spacer(Modifier.padding(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Cada quien paga")
                Text(
                    text = "$$porPersona",
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp
                )
                Text("entre $personas personas")
                Text("Sobrante por redondeo $$demas")
            }
        }
    }
}
