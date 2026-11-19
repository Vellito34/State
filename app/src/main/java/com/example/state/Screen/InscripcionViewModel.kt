package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InscripcionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataInscripcion())
    val uiState: StateFlow<DataInscripcion> = _uiState.asStateFlow()

    fun nueva() {
        _uiState.value = DataInscripcion()
    }

    fun nombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }

    fun correoChange(correo: String) {
        _uiState.update { it.copy(correo = correo) }
    }

    fun edadChange(edad: String) {
        _uiState.update { it.copy(edad = edad.filter { it.isDigit() } ) }
    }

    fun tallerChange(taller: String) {
        _uiState.update { it.copy(taller = taller) }
    }

    fun avisoChange(boolean: Boolean) {
        _uiState.update { it.copy(aceptaAviso = boolean) }
    }

    fun inscribir() {
        _uiState.update { it.copy(inscrito = true) }
    }

    fun nuevo() {
        _uiState.value = DataInscripcion()
    }
}