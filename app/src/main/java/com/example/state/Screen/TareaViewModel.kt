package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TareaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataTarea())
    val uiState: StateFlow<DataTarea> = _uiState.asStateFlow()

    fun borrarCompletadas() {
        _uiState.update { s ->
            s.copy(
                lista = s.lista.filter { !it.hecha },
                restablecer = false
            )
        }
    }
    fun vaciar() {
        _uiState.update { DataTarea() }
    }

    fun tituloChange(titulo: String) {
        _uiState.update { it.copy(titulo = titulo) }
    }

    fun prioridadChange(prioridad: String) {
        _uiState.update { it.copy(prioridad = prioridad ) }
    }

    fun filtroChange(filtro: String) {
        _uiState.update { it.copy(filtro = filtro) }
    }

    fun sirestablecerChange(){
        _uiState.update { it.copy(restablecer = true) }
    }

    fun norestablecerChange(){
        _uiState.update { it.copy(restablecer = false) }
    }

    fun save() {
        _uiState.update { s ->
            if (s.titulo.isNotBlank()) {
                s.copy(
                    lista = s.lista + Tarea(
                        id = s.id,
                        titulo = s.titulo,
                        prioridad = s.prioridad
                    ), id = s.id + 1, titulo = "", prioridad = "Media"
                )
            }
            else {
                s
            }
        }

    }

    fun delete(id: Int){
        _uiState.update { d ->
            d.copy(lista = d.lista.filter { it.id != id })
        }
    }

}