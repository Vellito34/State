package com.example.state.Screen

import android.R.attr.rating
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

fun califi(valor: Int): String = when (valor) {
    2 -> "Regular"
    3 -> "Bueno"
    4 -> "Muy bueno"
    5 -> "Excelente"
    else -> "Malo"
}

@Composable
fun InicioScreen(modifier: Modifier,viewModel: ReseñaViewModel = viewModel() ) {

    val state by viewModel.uiSate.collectAsStateWithLifecycle()

    if (!state.publicada) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(24.dp)) {
            item {
                Text(text = "Restaurante El Mezquite", fontWeight = FontWeight.Bold)
                Text(text = "Califica tu experiencia", fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.padding(10.dp))
                estrellas("Comida", state.comida, viewModel::comidaChange)
                Spacer(modifier = Modifier.padding(10.dp))
                estrellas("Servicio", state.servicio, viewModel::servicioChange)
                Spacer(modifier = Modifier.padding(10.dp))
                estrellas("Ambiente", state.ambiente, viewModel::ambienteChange)
                Spacer(modifier = Modifier.padding(15.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Calificacion genereal")
                            Row() {
                                Text(text = "${state.caliGeneral}")
                                Box(
                                    modifier = Modifier
                                        .background(color = Color.Yellow, shape = CircleShape)
                                        .size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.padding(10.dp))
                OutlinedTextField(
                    value = state.comentario,
                    onValueChange = viewModel::comentarioChange,
                    label = { Text("Escribe un comentario") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (state.comentario.length == 200) Text("Se llegaron a los carracteres maximos") else Text(
                            text = "${state.comentario.length}/200",
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    isError = state.comentario.length >= 180,
                )
            }
            item {
                Spacer(modifier = Modifier.padding(10.dp))
                etiquetas(state.eitquetaSel, viewModel::etiquetaChange)
            }
            item {
                Button(
                    onClick = viewModel::publicar,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = (state.validar) )
                    { Text("Publicar") }
            }
        }
    } else{
        Card(modifier = Modifier.padding(15.dp)) {
            Column(modifier =  Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Comida : ${state.comida}")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Servicio : ${state.servicio}")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Ambiente : ${state.ambiente}")
                Spacer(modifier = Modifier.padding(5.dp))
                Text("Etiquetas: ")
                state.eitquetaSel.forEach { e -> Text(text = e) }
                Spacer(modifier = Modifier.padding(5.dp))
                Text("Comentario: \n${state.comentario}", textAlign = TextAlign.Center)

            }
        }
    }
}

@Composable
fun estrellas(nombre: String, calificacion: Int, caliCambio: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = nombre, fontSize = 24.sp)
        Spacer(Modifier.padding(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(0.6f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                for (i in 1..5) {
                    FilterChip(
                        selected = i <= calificacion,
                        onClick = {
                            if (calificacion == i) {
                                caliCambio(i - 1)
                            } else {
                                caliCambio(i)
                            }
                        },
                        label = {},
                        shape = CircleShape,
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color.Yellow),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Text(text = califi(calificacion), color = Color.Blue)
        }
    }
}

@Composable
fun etiquetas(list: List<String>, listChange: (List<String>) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        listOf("Familiar", "Economico", "Rapido", "Romantico", "Vegetariano").forEach { e ->
            val activo = e in list
            FilterChip(
                selected = activo,
                onClick = {
                    val nuevo = if (activo) {
                        list - e
                    } else {
                        list + e
                    }
                    listChange(nuevo)
                },
                label = { Text(e) }
            )
        }
    }
}