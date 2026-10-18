package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun InicioScreen(modifier: Modifier) {

    var x by rememberSaveable() { mutableStateOf("") }
    var y by rememberSaveable() { mutableStateOf("") }
    var ans by rememberSaveable() { mutableStateOf(0) }

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(10.dp))

                Text("Resultado: " + ans, fontSize = 50.sp)

                Spacer(modifier = Modifier.padding(10.dp))

                TextField(
                    value = x,
                    onValueChange = { n -> x = n.filter { it.isDigit() } },
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
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {

                    if (!x.isEmpty() || !y.isEmpty()) {
                        ans = x.toInt() + y.toInt()
                    }
                }, modifier = Modifier.weight(0.5f)) {
                    Text("Sumar")
                }


                Spacer(modifier = Modifier.padding(10.dp))

                Button(onClick = {
                    if (!x.isEmpty() || !y.isEmpty()) ans = x.toInt() - y.toInt()
                }, modifier = Modifier.weight(0.5f)) {
                    Text("Restar")
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                Button({
                    if (!x.isEmpty() || !y.isEmpty()) ans = x.toInt() * y.toInt()
                }, modifier = Modifier.weight(0.5f)) { Text("Multiplicar") }
                Spacer(modifier = Modifier.padding(10.dp))

                Button({
                    if (!x.isEmpty() || !y.isEmpty() || !y.equals("0")) ans = x.toInt() / y.toInt()
                }, modifier = Modifier.weight(0.5f)) { Text("Dividir") }
            }
        }
    }
}

@Preview
@Composable
fun inciopreview() {
    InicioScreen(modifier = Modifier.fillMaxSize())
}