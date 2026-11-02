package com.example.state.Screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Tarea(val id: Int, val titulo : String,val prioridad: String , hecha: Boolean = false) {

    var hecha by mutableStateOf(hecha)
}