package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InicioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    private fun cambiarCantidad(nombre: String, op: Int) {
        _uiState.update { d ->
            d.copy(
                productos = d.productos.map { p ->
                    if (p.nombre == nombre)
                        p.copy(cantidad = (p.cantidad + op))
                    else p
                }
            )
        }
    }

    fun incrementar(nombre: String) = cambiarCantidad(nombre, +1)
    fun quitar(nombre: String) = cambiarCantidad(nombre, -1)

    fun vaciar() {
        _uiState.update { d ->
            d.copy(productos = d.productos.map { it.copy(cantidad = 0) })
        }
    }

    fun abrirConfirmacion() {
        _uiState.update { it.copy(mostrarConfirmacion = true) }
    }

    fun cerrarConfirmacion() {
        _uiState.update { it.copy(mostrarConfirmacion = false) }
    }

    fun maximo() = _uiState.value.MAX
}