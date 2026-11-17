package com.example.state.Screen

data class QuizUiState(
    val pantalla: Pantalla = Pantalla.INICIO,
    val nombre: String = "",
    val preguntas: List<Pregunta> = emptyList(),
    val preguntaActual: Int = 0,
    val seleccionada: Int =-1,
    val respondio: Boolean = false,
    val correctas: Int = 0
) {
    val totalPreguntas get() = preguntas.size
    val pregunta get() = preguntas[preguntaActual]
    val esUltima get() = preguntaActual == totalPreguntas - 1
}