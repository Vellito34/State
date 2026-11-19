package com.example.state.Screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

data class Producto(val nombre: String, val precio: Double = 0.00, val cantidad: Int = 0) {

}