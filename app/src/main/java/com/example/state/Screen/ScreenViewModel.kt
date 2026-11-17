package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DataScreen())
    val uiState: StateFlow<DataScreen> = _uiState.asStateFlow()

    fun compacto() {
        _uiState.update { u ->
            u.copy(interlineado =  "Compacto")
        }
    }

    fun normal() {
        _uiState.update { u ->
            u.copy(interlineado =  "Normal")
        }
    }

    fun amplio() {
        _uiState.update { u ->
            u.copy(interlineado =  "Amplio")
        }
    }

    fun cambioModo(activado: Boolean) {
        _uiState.update { u ->
            if (u.modo){
                u.copy(modo = true)
            } else{
                u.copy(modo = false)
            }
        }
    }

    fun serif() {
        _uiState.update { u ->
            u.copy(fuente =  "Serif")
        }
    }

    fun sans() {
        _uiState.update { u ->
            u.copy(fuente =  "Sans")
        }
    }

    fun reiniciar(){
        _uiState.value = DataScreen()
    }

    fun cambiarTamano(valor: Float) {
        _uiState.update { it.copy(posicionSlader = valor) }
    }
}