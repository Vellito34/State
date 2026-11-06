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

@Composable
fun Screen(modifier: Modifier) {
    var nombre by rememberSaveable() { mutableStateOf("") }
    var pantalla by rememberSaveable() { mutableStateOf(Pantalla.INICIO) }
    var puntaje by rememberSaveable() { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        when (pantalla) {
            Pantalla.INICIO -> InicioScreen({ pantalla = it }, { nombre = it })
            Pantalla.QUIZ ->  preguntaPantalla({ pantalla = it }, { puntaje = it })
            else -> {
                ResultadosScreen(puntaje, nombre, { pantalla = it })
            }
        }

    }
}

@Composable
fun InicioScreen(cambio: (Pantalla) -> Unit, Upnombre: (String) -> Unit) {
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
                cambio(Pantalla.QUIZ)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.length >= 3
        ) { Text("Comenzar") }

    }
}

@Composable
fun preguntaPantalla(cambio: (Pantalla) -> Unit, puntaje: (Int) -> Unit) {
    var correctas by rememberSaveable() { mutableStateOf(0) }
    var lista = rememberSaveable() {
        mutableStateListOf(
            Pregunta("Pregunta 1", listOf("a", "b", "c", "d"), 0),
            Pregunta("Pregunta 2", listOf("a", "b", "c", "d"), 1),
            Pregunta("Pregunta 3", listOf("a", "b", "c", "d"), 2),
            Pregunta("Pregunta 4", listOf("a", "b", "c", "d"), 3),
            Pregunta("Pregunta 5", listOf("a", "b", "c", "d"), 0)
        )
    }
    var preguntaActual by rememberSaveable() { mutableStateOf(0) }
    val totalPregunta = lista.size
    var seleccionada by rememberSaveable() { mutableStateOf(-1) }
    var respondio by rememberSaveable() { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Quiz de programacion")
        Spacer(Modifier.padding(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Pregunta ${preguntaActual + 1} de $totalPregunta", fontSize = 15.sp)
            Text(text = "Puntaje: $correctas")
        }
        LinearProgressIndicator(
            progress = { preguntaActual / totalPregunta.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.padding(10.dp))
        Text(text = lista[preguntaActual].texto, fontWeight = FontWeight.Bold)
        lista[preguntaActual].opciones.forEachIndexed { i, opc ->
            var color = when {
                !respondio -> Color.Transparent
                i == lista[preguntaActual].correcta -> Color.Green
                i == seleccionada -> Color.Red
                else -> Color.Transparent
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = seleccionada == i,
                    onClick = { seleccionada = i },
                    enabled = !respondio
                )
                Text(text = opc, fontWeight = FontWeight.Bold)
            }


        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    if (seleccionada == lista[preguntaActual].correcta) correctas++
                    respondio = true
                },
                enabled = seleccionada != -1 && !respondio,
                modifier = Modifier.fillMaxWidth(0.4f)
            ) { Text("Confirmar") }

            Button(
                onClick = {
                    if (preguntaActual == totalPregunta - 1) {
                        puntaje(correctas)
                        cambio(Pantalla.RESULTADO)
                    } else {
                        preguntaActual++
                        seleccionada = -1
                        respondio = false
                    }
                },
                enabled = respondio,
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (preguntaActual == totalPregunta - 1) "Terminar" else "Siguiete") }
        }
    }

}

@Composable
fun ResultadosScreen(puntaje: Int, nombre: String, cambio: (Pantalla) -> Unit) {
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
                        cambio(Pantalla.QUIZ)
                    },
                ) { Text("Reintentar") }
                Spacer(Modifier.padding(10.dp))

                    Button(
                        onClick = {
                            cambio(Pantalla.INICIO)
                        },
                    ) { Text("Cambiar Jugador") }

            }


        }
    }
}