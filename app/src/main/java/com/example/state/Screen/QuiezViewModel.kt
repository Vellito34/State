package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuiezViewModel : ViewModel(){

    private val banco = listOf(
        Pregunta("Pregunta 1", listOf("a", "b", "c", "d"), 0),
        Pregunta("Pregunta 2", listOf("a", "b", "c", "d"), 1),
        Pregunta("Pregunta 3", listOf("a", "b", "c", "d"), 2),
        Pregunta("Pregunta 4", listOf("a", "b", "c", "d"), 3),
        Pregunta("Pregunta 5", listOf("a", "b", "c", "d"), 0)
    )

    private val _uiState = MutableStateFlow(QuizUiState(preguntas =  banco))
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onNombreCambio(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }

    private fun reiniciarQuiz() {
        _uiState.update {
            it.copy(
                pantalla = Pantalla.QUIZ,
                preguntaActual = 0,
                seleccionada = -1,
                respondio = false,
                correctas = 0
            )
        }
    }

    fun comenzar() {
        if (_uiState.value.nombre.length < 3) return
        reiniciarQuiz()
    }

    fun seleccionar(indice: Int) {
        _uiState.update { it.copy(seleccionada = indice) }
    }

    fun confirmar() {
        _uiState.update { s ->
            if (s.seleccionada == -1 || s.respondio) {
                s
            } else  {
                s.copy(
                    respondio = true,
                    correctas = if(s.seleccionada == s.pregunta.correcta) {
                        s.correctas + 1
                    } else{
                        s.correctas
                    }
                )
            }
        }
    }

    fun siguiente() {
        _uiState.update { s ->
            if(!s.respondio) {
                s
            } else if (s.esUltima){
                s.copy(pantalla = Pantalla.RESULTADO)
            } else {
                s.copy(
                    preguntaActual = s.preguntaActual + 1,
                    seleccionada = -1,
                    respondio = false
                )
            }
        }
    }

    fun reintentar() = reiniciarQuiz()

    fun cambiarJugador() {
        _uiState.value = QuizUiState(preguntas = banco)
    }

}