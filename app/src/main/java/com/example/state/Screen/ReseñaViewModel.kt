package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.compareTo

class ReseñaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataReseña())
    val uiSate: StateFlow<DataReseña> = _uiState.asStateFlow()

    fun comidaChange(dato: Int) {
        _uiState.update { it.copy(comida = dato) }
    }

    fun servicioChange(dato: Int) {
        _uiState.update { it.copy(servicio = dato) }
    }

    fun ambienteChange(dato: Int) {
        _uiState.update { it.copy(ambiente = dato) }
    }

    fun comentarioChange(comentario: String) {
        _uiState.update { s ->
            if (s.comentario.length <= 200) {
                s.copy(comentario = comentario)
            } else {
                s
            }
        }
    }

    fun etiquetaChange(list: List<String>){
        _uiState.update { it.copy(eitquetaSel = list) }
    }

    fun publicar(){
        _uiState.update { it.copy(publicada = true) }
    }
}
