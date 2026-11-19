package com.example.state.Screen;

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

public class DatosViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DatosUi())
    val uiState = _uiState.asStateFlow()

    fun montoChange(monto:String){
        _uiState.update { it.copy(monto = monto) }
    }

    fun propinaChange(propina: Double) {
        _uiState.update({it.copy(propina = propina)})
    }

    fun menosCantidad() {
        _uiState.update { it.copy(cantidad = it.cantidad - 1) }
    }

    fun masCantidad() {
        _uiState.update { it.copy(cantidad = it.cantidad + 1) }
    }

    fun redondear(boolean: Boolean) {
        _uiState.update { it.copy(rendondear = boolean) }
    }
}
