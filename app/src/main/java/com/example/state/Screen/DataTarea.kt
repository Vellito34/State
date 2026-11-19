package com.example.state.Screen

data class DataTarea(
    val titulo: String = "",
    val prioridad: String = "",
    val lista: List<Tarea> = emptyList(),
    val id: Int = 0,
    val filtro: String = "Todas",
    val restablecer: Boolean = false,
    )
