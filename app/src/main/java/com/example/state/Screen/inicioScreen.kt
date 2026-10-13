package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InicioScreen(modifier: Modifier, numero: Int, miclick: () -> Unit, other: () -> Unit) {
    var y by rememberSaveable() { mutableStateOf("0") }
    var x by rememberSaveable() { mutableStateOf("0") }
    var ans by rememberSaveable() { mutableStateOf(0) }

    Column(modifier = modifier) {
        TextField(
            value = x,
            onValueChange = { nuevo -> x = nuevo.filter { it.isDigit() } },
            label = { Text("Numero 1") },
            placeholder = { Text("Escribe el valor 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = y,
            onValueChange = { nuevo -> y = nuevo.filter { it.isDigit() } },
            label = { Text("Numero 2") },
            placeholder = { Text("Escribe el valor 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if (x.isEmpty()){return@Button}
            ans = x.toInt() + y.toInt() }) {
            Text("Sumar")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {ans = x.toInt() - y.toInt()}) {
            Text("Restar")
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Text("Resultado: " + ans, fontSize = 50.sp)
    }
}

