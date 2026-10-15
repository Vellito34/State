package com.example.state.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.collections.mutableListOf

@Composable
fun miCard(modifier: Modifier = Modifier) {
    val nombre = rememberSaveable { mutableStateListOf<String>() }
    var name by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            TextField(
                value = name,
                onValueChange = { texto -> name = texto },
                label = { Text("Nombre") },
                placeholder = { Text("Escribe un nombre") }
            )

            Row() {

                Button(onClick = {
                    if (name.isNotBlank()) {
                        nombre.add(name)
                        name = ""
                    }
                }
                ) { Text("Guardar") }

                Button(onClick = {
                    if (name.isNotBlank()) {
                        nombre.remove(name)
                        name = ""
                    }
                }) { Text("Eliminar") }
            }
        }

        items(nombre) { nom ->
            Card(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = modifier.fillMaxSize()) {
                    Text(
                        text = nom,
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = {
                        nombre.remove(nom)
                    }) { Text("X") }
                }
            }
        }

    }
}