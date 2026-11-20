package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GastosViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataGastos())
    val uiState: StateFlow<DataGastos> = _uiState.asStateFlow()

    fun ingresoChange(ingreso: String) {

        _uiState.update { s -> if (s.monto >= 0) s.copy(ingreso = ingreso) else s }
    }

    fun addGasto() {
        _uiState.update {
            it.copy(
                gastos = it.gastos + it.nuevoGastos,
                concepto = "",
                costo = "",
                categoria = ""
            )
        }
    }

    fun nuevoGastoChange(gastos: Gastos) {
        _uiState.update { it.copy(nuevoGastos = gastos) }
    }

    fun agregarChange(boolean: Boolean) {
        _uiState.update { it.copy(agregar = boolean) }
    }

    fun falseAgregar() {
        _uiState.update { it.copy(agregar = false) }
    }

    fun conceptoChange(dato: String) {
        _uiState.update { it.copy(concepto = dato) }
    }

    fun costoChange(dato: String) {
        _uiState.update { it.copy(costo = dato) }
    }

    fun expandidoChange(boolean: Boolean) {
        _uiState.update { it.copy(expandido = boolean) }
    }

    fun expandidoFalse() {
        _uiState.update { it.copy(expandido = false) }
    }

    fun categoriaChange(dato: String) {
        _uiState.update { it.copy(categoria = dato, expandido = false) }
    }

    fun eliminarGasto(num: Int) {
        _uiState.update { it.copy(gastos = it.gastos.filterIndexed { i, g -> i != num }) }
    }
}