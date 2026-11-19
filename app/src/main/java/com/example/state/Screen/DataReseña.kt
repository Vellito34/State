package com.example.state.Screen

data class DataReseña(
    val comida: Int = 0,
    val servicio: Int = 0,
    val ambiente: Int = 0,
    val comentario: String = "",
    val eitquetaSel: List<String> = emptyList(),
    val publicada: Boolean = false,
) {
    val caliGeneral: Double get() = (comida + ambiente + servicio).toDouble() / 3
    val validar: Boolean get() {if (caliGeneral != 0.0 && comentario.length >= 20) return true else return false}
}
