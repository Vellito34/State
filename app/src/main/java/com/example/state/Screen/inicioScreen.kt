package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Switch
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun screen(modifier: Modifier = Modifier, viewModel: ScreenViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()

    var interElegido = when (state.interlineado) {
        "Compacto" -> 1f
        "Normal" -> 1.3f
        else -> 1.6f
    }
    var funteUsada = when (state.fuente) {
        "Serif" -> FontFamily.Serif
        else -> FontFamily.SansSerif
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.padding(10.dp))
            Row() {
                Button(onClick = {}) { Text("<-") }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Ajustes de lectura", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }
        }
        item {
            Spacer(modifier = Modifier.padding(10.dp))
            Box() {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tamaño de letra")
                    Text(text = "${state.posicionSlader} sp", color = Color.Blue)

                }
            }

            Slider(
                value = state.posicionSlader,
                onValueChange = viewModel::cambiarTamano,
                valueRange = 14f..32f,
                steps = 17
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "A", fontSize = 14.sp)
                Text(text = "A", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.padding(20.dp))

        }

        item {
            Text(text = "Interlineado")
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = viewModel::compacto) { Text("Compacto") }
                Button(onClick = viewModel::normal) { Text("Normal") }
                Button(onClick = viewModel::amplio) { Text("Amplio") }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo oscuro")
                Switch(checked = state.modo, onCheckedChange = viewModel::cambioModo)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Fuente")
                Box() {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = viewModel::serif) { Text(text = "Serif") }

                        Spacer(Modifier.padding(10.dp))
                        Button(onClick = viewModel::sans) { Text(text = "Sans") }
                    }
                }
            }
        }

        item {

            Text(text = "VISTA PREVIA")

            Card(
                modifier = Modifier
                    .fillMaxSize(), colors = CardDefaults.cardColors(
                    containerColor = when (state.modo) {
                        true -> Color(0xFF121212)
                        false -> Color.White
                    },
                    contentColor = when (state.modo) {
                        true -> Color.White
                        false -> Color.Black
                    }

                )
            ) {
                Text(
                    text = "Era una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por que",
                    fontSize = state.posicionSlader.sp,
                    lineHeight = (state.posicionSlader * interElegido).sp,
                    fontFamily = funteUsada,
                    modifier = Modifier.padding(15.dp)

                )
            }

            Spacer(Modifier.padding(20.dp))

            Button(
                onClick = viewModel::reiniciar,
                modifier = Modifier.fillMaxSize()
            ) { Text(text = "Restablecer") }
        }

    }
}

@Preview
@Composable
fun previewscreen() {
    screen(modifier = Modifier.fillMaxSize())
}