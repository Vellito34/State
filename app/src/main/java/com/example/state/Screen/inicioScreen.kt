package com.example.state.Screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import kotlin.collections.mutableListOf

@Composable
fun miCard(modifier: Modifier = Modifier) {
    val nombre = rememberSaveable { mutableStateListOf<String>()}
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

            Button(onClick = {
                if (name.isNotBlank()){
                    nombre.add(name)
                    name =""
                }
            }
            ) { Text("Guardar")}
        }


        items(nombre) { nombre ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = nombre,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}