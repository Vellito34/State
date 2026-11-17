package com.example.state.Screen

data class DataAfore(
    val personasActuales: Int = 0,
    val visitasDeDia: Int = 0,
    val capacidad: Int = 50,
){
    val porcentaje: Float get() = personasActuales / capacidad.toFloat()
}
