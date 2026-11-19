package com.example.state.Screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class loginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DataLogin())
    val uiState: StateFlow<DataLogin> = _uiState.asStateFlow()

    fun correoChange(correo: String){
        _uiState.update { it.copy(correo = correo) }
    }

    fun contraChange(contra: String){
        _uiState.update { it.copy(contra = contra) }
    }

    fun verContraChange(){
        _uiState.update { it.copy(verContra = !it.verContra) }
    }

    fun recordarChange(boolean: Boolean){
        _uiState.update { it.copy(recordar = boolean) }
    }

    fun correcto(){
        _uiState.update { s ->
            if (s.correo != "paciente@vida.mx" && s.contra != "Vida2025") {
                s.copy(
                intentos = s.intentos + 1,
                correo = "",
                contra = ""
                )
            } else {
                s.copy(correcto = true)
            }
        }
    }

    fun correctoChange(){
        _uiState.update { it.copy(correcto = false) }
    }
}