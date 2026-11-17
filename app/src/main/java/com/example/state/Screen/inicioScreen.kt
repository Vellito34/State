package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Shapes
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
import androidx.compose.runtime.collectAsState

@Composable
fun Screen(modifier: Modifier, viewModel: QuiezViewModel = viewModel) {

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        when (state.pantalla) {
            Pantalla.INICIO -> InicioScreen(
                nombre = state.nombre,
                Upnombre = viewModel::onNombreCambio,
                viewModel::comenzar
            )

            Pantalla.QUIZ -> preguntaPantalla(
                state = state,
                onSeleccionar = viewModel::seleccionar,
                onConfirmar = viewModel::confirmar,
                onSiguiente = viewModel::siguiente
            )

            else -> {
                ResultadosScreen(
                    puntaje = state.correctas,
                    nombre = state.nombre,
                    onReintentar = viewModel::reintentar,
                    onCambiarJugador = viewModel::cambiarJugador
                )
            }
        }

    }
}

@Composable
fun InicioScreen(nombre: String, Upnombre: (String) -> Unit, onComenzar: () -> Unit) {
    var nombre by rememberSaveable() { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Escribe tu nombre") },
            isError = nombre.length < 3,
            supportingText = { if (nombre.length < 3) Text("Debe de tener mas de 3 caracters") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                Upnombre(nombre)
                onComenzar()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.length >= 3
        ) { Text("Comenzar") }

    }
}

@Composable
fun preguntaPantalla(
    state: QuizUiState,
    onSeleccionar: (Int) -> Unit,
    onConfirmar: () -> Unit,
    onSiguiente: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Quiz de programacion")
        Spacer(Modifier.padding(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Pregunta ${state.preguntaActual + 1} de ${state.totalPreguntas}",
                fontSize = 15.sp
            )
            Text(text = "Puntaje: ${state.correctas}")
        }
        LinearProgressIndicator(
            progress = { state.preguntaActual / state.totalPreguntas.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(10.dp))
        Text(text = state.pregunta.texto, fontWeight = FontWeight.Bold)
        state.pregunta.opciones.forEachIndexed { i, opc ->
            var color = when {
                !state.respondio -> Color.Transparent
                i == state.pregunta.correcta -> Color.Green
                i == state.seleccionada -> Color.Red
                else -> Color.Transparent
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.seleccionada == i,
                    onClick = { onSeleccionar(i) },
                    enabled = !state.respondio
                )
                Text(text = opc, fontWeight = FontWeight.Bold)
            }


        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    onConfirmar
                },
                enabled = state.seleccionada != -1 && !state.respondio,
                modifier = Modifier.fillMaxWidth(0.4f)
            ) { Text("Confirmar") }

            Button(
                onClick = {
                    onSiguiente
                },
                enabled = state.respondio,
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (state.esUltima) "Terminar" else "Siguiete") }
        }
    }

}

@Composable
fun ResultadosScreen(
    puntaje: Int,
    nombre: String,
    onReintentar: () -> Unit,
    onCambiarJugador: () -> Unit
) {
    var Resultados = when (puntaje) {
        5 -> "Excelente, $nombre!"
        in 3..4 -> "Buen trabajo, $nombre!"
        else -> "Sigue practicando, $nombre!"
    }
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card() {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = Resultados)

                Button(
                    onClick = {
                        onReintentar
                    },
                ) { Text("Reintentar") }
                Spacer(Modifier.padding(10.dp))

                Button(
                    onClick = {
                        onCambiarJugador
                    },
                ) { Text("Cambiar Jugador") }

            }


        }
    }
}