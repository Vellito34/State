package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

fun colores(prioridad: String): Color = when (prioridad) {

    "Alta" -> Color.Red
    "Media" -> Color.Yellow
    "Baja" -> Color.Green

    else -> {
        Color.Green
    }
}

@Composable
fun InicioScreen(modifier: Modifier,viewModel: TareaViewModel = viewModel() ) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()


    LazyColumn(modifier = modifier, contentPadding = PaddingValues(24.dp)) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = state.titulo,
                    onValueChange = viewModel::tituloChange,
                    label = { Text("Nueva tarea") },
                    modifier = Modifier
                        .weight(0.7f)
                        .height(64.dp)
                )
                Button(
                    onClick = viewModel::save,
                    modifier = Modifier
                        .height(64.dp)
                        .weight(0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) { Text("+") }
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Prioridad")
                prioridad(state.prioridad, viewModel::prioridadChange)
            }
            Spacer(Modifier.padding(10.dp))
            filtros(state.filtro, viewModel::filtroChange)
        }

        item {
            lista(state.lista, viewModel::delete, state.filtro)
            Spacer(Modifier.padding(10.dp))
            Button(
                onClick = viewModel::sirestablecerChange,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Borrar completadas") }
        }
    }
    if (state.restablecer) {
        AlertDialog(
            onDismissRequest = viewModel::norestablecerChange,
            title = { Text("Borrar completadas") },
            text = { Text(" Seguro que quieres eliminar las tareas completadas?") },
            confirmButton = {
                TextButton(onClick =  viewModel::borrarCompletadas ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::norestablecerChange) { Text("Cancelar") }
            }
        )
    }

}

@Composable
fun filtros(fil: String, Upfiltro: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Todas", "Pendientes", "Hechas").forEach { f ->
            FilterChip(
                selected = fil == f,
                onClick = { Upfiltro(f) },
                label = { Text(f) }
            )
        }
    }
}

@Composable
fun prioridad(prioridad: String, UpPrioridad: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Alta", "Media", "Baja").forEach { p ->
            FilterChip(
                selected = prioridad == p,
                onClick = {
                    UpPrioridad(p)
                },
                label = { Text(p) }
            )
        }
    }
}

@Composable
fun lista(lista: List<Tarea>, eliminar: (Int) -> Unit, filtro: String) {
    val mostrar = when (filtro) {
        "Pendientes" -> lista.filter { !it.hecha }
        "Hechas" -> lista.filter { it.hecha }
        else -> lista
    }
    Column() {
        mostrar.forEach { t ->
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Checkbox(
                        checked = t.hecha,
                        onCheckedChange = { t.hecha = it }
                    )
                    Column() {
                        Text(t.titulo)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = colores(t.prioridad)),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(
                                text = t.prioridad,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                IconButton(onClick = { eliminar(t.id) }) { Text("X") }
            }
        }
    }
    Text(text = "${lista.filter { !it.hecha }.size} pendiente de ${lista.size}")
}

