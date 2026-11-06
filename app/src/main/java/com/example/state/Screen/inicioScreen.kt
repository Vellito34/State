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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.collections.forEach

fun gastado(lista: List<Gastos>): Double {
    var gastado: Double = 0.00
    lista.forEach { g ->
        gastado += g.monto
    }
    return gastado
}

fun mayorGasto(list: List<Gastos>, mayor: (Gastos) -> Unit) {
    if (list.isNotEmpty()) {
        var gMayor: Gastos = list[0]
        list.forEach { g ->
            if (g.monto > gMayor.monto) {
                gMayor = g
            }
        }
        mayor(gMayor)
    }
}

fun colores(gastado: Double, monto: Double): Color = when (gastado / monto) {

    in 1.00..Double.MAX_VALUE -> Color.Red
    in 0.8.. .99 -> Color.Yellow
    else -> Color.Gray
}

@Composable
fun InicioScreen(modifier: Modifier) {
    var ingreso by rememberSaveable() { mutableStateOf("4500.00") }
    var monto = ingreso.toDoubleOrNull() ?: 0.00
    var gastos = remember() { mutableStateListOf<Gastos>() }
    var gastado = gastado(gastos)
    var Saldo = monto - gastado
    var slider = if (monto > 0) monto.toFloat() else 1f
    var gastadoSlider = gastado.toFloat().coerceIn(0f, slider)
    var gastoMayor: Gastos = Gastos("", 0.00, "")
    mayorGasto(gastos, { gastoMayor = it })
    var nuevoGasto by remember() { mutableStateOf(Gastos("", 0.00, "")) }
    var agregar by rememberSaveable() { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(24.dp)) {
        item {
            Text(text = "Mi presupuesto", fontWeight = FontWeight.Bold)
            Spacer(Modifier.padding(10.dp))
        }

        item {
            OutlinedTextField(
                value = ingreso,
                onValueChange = {
                    if (monto >= 0) {
                        ingreso = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Ingreso mensual") },
                isError = monto <= 0

            )
            Spacer(Modifier.padding(10.dp))
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colores(gastado, monto),
                    contentColor = Color.Black
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(modifier = Modifier.fillMaxHeight()) {
                            Text("Gastado")
                            Text(
                                text = "${gastado}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                        }

                        Column(modifier = Modifier.fillMaxHeight()) {
                            Text("Saldo")
                            Text(
                                text = "${Saldo}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = Color.Green
                            )
                        }
                    }
                    Slider(
                        value = gastadoSlider,
                        onValueChange = {},
                        valueRange = 0f..slider,
                        enabled = false,
                        colors = SliderDefaults.colors(
                            activeTrackColor = colores(gastado, monto),
                            thumbColor = Color.Transparent,
                            inactiveTickColor = Color.Gray
                        )
                    )
                    Text(text = "${(gastado / monto) * 100}% usado ~ Mayor gasto: ${gastoMayor}")
                }
            }
        }
        item {
            aviso(monto, gastado)
        }
        item {
            newGasto({ nuevoGasto = it }, { agregar = it })
            if (agregar) {
                gastos.add(nuevoGasto)
                agregar = false
            }
        }
        item {
            gastosRecientes(gastos, { gastos.removeAt(it) })
        }
    }
}

@Composable
fun aviso(monto: Double, gastado: Double) {
    if ((gastado / monto) >= 0.8) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colores(gastado, monto))
        ) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "^ Cuidado: pasaste el 80 %",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newGasto(ChangeGasto: (Gastos) -> Unit, Agregar: (Boolean) -> Unit) {
    var monto by rememberSaveable() { mutableStateOf("") }
    var gmonto = monto.toDoubleOrNull() ?: 0.00
    var concepto by rememberSaveable() { mutableStateOf("") }
    val categorias = listOf("Comida", "Transporte", "Renta", "Ocio", "Otros")

    var expandido by rememberSaveable() { mutableStateOf(false) }
    var categoria by rememberSaveable() { mutableStateOf("") }

    Column() {
        Text(text = "Nuevo gasto", fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = concepto,
            onValueChange = { concepto = it },
            label = { Text("Concepto") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(0.4f)
            )
            Spacer(Modifier.padding(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = it }
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { if (!expandido) Text("v") },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    categorias.forEach { opc ->
                        DropdownMenuItem(
                            text = { Text(opc) },
                            onClick = {
                                categoria = opc
                                expandido = false
                            }
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                ChangeGasto(Gastos(concepto, gmonto, categoria))
                Agregar(true)
                monto = ""
                concepto = ""
                categoria = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Agregar gasto") }
    }

}

@Composable
fun gastosRecientes(lista: List<Gastos>, upEliminar: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Gastos recientes")
        lista.forEachIndexed { i, g ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    Text(text = g.concepto, fontWeight = FontWeight.Bold)
                    Text(text = g.categoria)
                }
                Row() {
                    Text(text = "-$${g.monto}", color = Color.Red)
                    Button(
                        onClick = {
                            upEliminar(i)
                        }
                    ) { Text("X") }
                }
            }
        }
    }
}