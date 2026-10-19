package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.times
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.state.R

@Composable
fun afore(modifier: Modifier) {
    var personasActuales by rememberSaveable { mutableStateOf(0) }
    var visitasDelDia by rememberSaveable { mutableStateOf(0) }
    val capacidad: Int = 50
    var porcentaje = personasActuales / capacidad.toFloat()

    val fondo = when {
        porcentaje <= 0.7f -> colorResource(R.color.verdeClaro)
        porcentaje > 0.7f && porcentaje <= 0.99f -> colorResource(R.color.naranja)
        else -> colorResource(R.color.rojo)
    }

    val odjetos = when {
        porcentaje <= 0.7f -> colorResource(R.color.verde)
        porcentaje > 0.7f && porcentaje <= 0.99f -> Color.Yellow
        else -> Color.Red
    }


    LazyColumn() {

        item {
            Column() {
                Spacer(modifier = Modifier.padding(20.dp))

                Text(text = "Bibloteca Central", fontSize = 24.sp)

                Spacer(modifier = Modifier.padding(20.dp))

                Text("Sala de estudio ~ capacidad $capacidad")
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(fondo)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$personasActuales / $capacidad",
                        fontSize = 50.sp,
                        color = odjetos,
                        fontWeight = FontWeight.Bold
                    )

                    Text("pesonas en la sala")

                    Spacer(modifier = Modifier.padding(20.dp))

                    //linea de carga
                    LinearProgressIndicator(
                        progress = { porcentaje },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = odjetos,
                        trackColor = Color.White
                    )

                    Text(
                        text = "${(porcentaje * 100).toInt()} % de opcupación",
                        color = odjetos,
                        fontWeight = FontWeight.Bold
                    )

                    Text("Aforo disponible")


                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        personasActuales--
                    }, enabled = personasActuales != 0,
                    modifier = Modifier.weight(0.4f)
                ) { Text("- Sale") }

                Spacer(modifier = Modifier.weight(0.1f))

                Button(
                    onClick = {

                        personasActuales++
                        visitasDelDia++

                    },
                    enabled = personasActuales != capacidad,
                    modifier = Modifier.weight(0.4f)
                ) { Text("+ Entra") }

            }

            Card() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text("Visitas del dia")
                        Text(text = "Solo suma las entradas")
                    }

                    Text(
                        text = "$visitasDelDia",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )

                }
            }

            Button(
                onClick = {
                    personasActuales = 0
                    visitasDelDia = 0
                },
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Cerrar dia") }
        }
    }

}


@Preview
@Composable
fun previewAfore() {
    afore(modifier = Modifier.fillMaxSize())
}