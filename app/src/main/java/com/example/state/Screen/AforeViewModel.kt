package com.example.state.Screen

import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModel
import com.example.state.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AforeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataAfore())
    val uiState: StateFlow<DataAfore> = _uiState.asStateFlow()

    fun quitarpersonas() {
        _uiState.update { u ->
            if (u.personasActuales > 0) u.copy(u.personasActuales - 1) else u
        }
    }

    fun agregarPersonas() {
        _uiState.update { u ->
            if (u.personasActuales < u.capacidad) u.copy(personasActuales = u.personasActuales + 1, visitasDeDia = u.visitasDeDia + 1) else u
        }
    }

    fun agregar5Personas() {
            if (_uiState.value.personasActuales < (_uiState.value.capacidad - 4)) {
                repeat(5) { agregarPersonas() }
            }
    }

    fun corte() {
        _uiState.value = DataAfore()
    }

}