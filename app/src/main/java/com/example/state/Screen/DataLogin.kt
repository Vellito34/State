package com.example.state.Screen

data class DataLogin(
    val correo:String = "",
    val contra: String = "",
    val recordar: Boolean = false,
    val intentos: Int = 0,
    val correcto: Boolean = false,
    val verContra: Boolean = false
)
