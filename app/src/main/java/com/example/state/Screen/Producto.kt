package com.example.state.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class Producto(var nombre: String, precio: Double = 0.00, cantidad : Int = 0) {
    var cantidad by mutableStateOf(cantidad)
    var precio by mutableStateOf(precio)

}